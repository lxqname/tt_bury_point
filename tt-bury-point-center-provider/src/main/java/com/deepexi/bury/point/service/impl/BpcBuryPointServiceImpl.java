package com.deepexi.bury.point.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.deepexi.bury.point.domain.dto.BpcBuryPointDto;
import com.deepexi.bury.point.domain.dto.BuryPointMessageDto;
import com.deepexi.bury.point.domain.dto.Message;
import com.deepexi.bury.point.domain.eo.BpcBuryPoint;
import com.deepexi.bury.point.domain.eo.BuryPointMessage;
import com.deepexi.bury.point.extension.AppRuntimeEnv;
import com.deepexi.bury.point.mapper.BpcBuryPointMapper;
import com.deepexi.bury.point.mapper.BuryPointMessageMapper;
import com.deepexi.bury.point.service.BpcBuryPointService;
import com.deepexi.common.util.UuidUtils;
import com.deepexi.util.BeanPowerHelper;
import com.deepexi.util.pageHelper.PageBean;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;


@Component
@Service(version = "${demo.service.version}")
public class BpcBuryPointServiceImpl implements BpcBuryPointService {


    @Value("${spring.profiles.active}")
    private String profile;

    private static final Logger logger = LoggerFactory.getLogger(BpcBuryPointServiceImpl.class);


    @Resource
    private Executor executor;

    private static final String TYPE_KEY = "action";

    @Value("${message.topic}")
    private String topic;


    @Resource
    private BpcBuryPointMapper bpcBuryPointMapper;

    @Resource
    private BuryPointMessageMapper buryPointMessageMapper;

    @Autowired
    private AppRuntimeEnv appRuntimeEnv;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public PageBean findPage(BpcBuryPointDto eo, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<BpcBuryPoint> list = bpcBuryPointMapper.findList(eo);
        return new PageBean<>(list);
    }

    @Override
    public List<BpcBuryPoint> findAll(BpcBuryPointDto eo) {
        List<BpcBuryPoint> list = bpcBuryPointMapper.findList(eo);
        return list;
    }
    @Override
    public BpcBuryPoint detail(String pk) {
        return bpcBuryPointMapper.selectById(pk);
    }

    @Override
    public Boolean create(BpcBuryPointDto eo) {
        String content = eo.getContent();
        JSONObject event = JSONUtil.parseObj(content);
        Message message = new Message();
        message.setId(UuidUtils.randomUUID());
        message.setEnv(profile);
        message.setTime(DateUtil.formatDateTime(new Date()));
        message.setType(event.getStr(TYPE_KEY));
        message.setEvent(event);


        executor.execute(() -> {
            //数据发送到kafka
            ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send(topic, JSONUtil.toJsonStr(message));
            send.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    logger.info("发送失败！ {}", throwable.getMessage());
                    //发送失败 入库暂存
                    BuryPointMessage buryPointMessage = BeanPowerHelper.mapPartOverrider(message, BuryPointMessage.class);
                    buryPointMessage.setEvent(JSONUtil.toJsonStr(message.getEvent()));
                    buryPointMessageMapper.insert(buryPointMessage);
                }

                @Override
                public void onSuccess(SendResult<String, String> stringStringSendResult) {
                    logger.info("发送成功！ {}", stringStringSendResult.toString());
                }
            });
        });
        return true;
    }

    @Override
    public Boolean update(String pk,BpcBuryPointDto eo) {
        eo.setId(pk);
        int result = bpcBuryPointMapper.updateById(BeanPowerHelper.mapPartOverrider(eo,BpcBuryPoint.class));
        return result > 0;
    }

    @Override
    public Boolean delete(String...pk) {
        int result = bpcBuryPointMapper.deleteByIds(pk);
        return result > 0;
    }


    @Override
    public void reSendFailedMessage() {
        List<BuryPointMessage> list = buryPointMessageMapper.findList(new BuryPointMessageDto());
        list.stream().forEach(t -> {
            Message message = new Message();
            BeanUtils.copyProperties(t, message, "event");
            message.setEvent(JSONUtil.parseObj(t.getEvent()));
            executor.execute(() -> {
                ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send(topic, JSONUtil.toJsonStr(message));
                send.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        logger.info("重新发送失败！ {}", throwable.getMessage());
                    }

                    @Override
                    public void onSuccess(SendResult<String, String> stringStringSendResult) {
                        logger.info("重新发送成功！ {}", stringStringSendResult.toString());
                        //删除该记录 物理删除
                        int i = buryPointMessageMapper.realDelete(t.getId());
                        if (i > 0) {
                            logger.info("删除该条消息成功！id: {}", t.getId());
                        }
                    }
                });
            });
        });

    }



}