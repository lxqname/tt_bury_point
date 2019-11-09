package com.deepexi.bury.point.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.deepexi.bury.point.domain.dto.BpcBuryPointDto;
import com.deepexi.bury.point.domain.eo.BpcBuryPoint;
import com.deepexi.bury.point.extension.AppRuntimeEnv;
import com.deepexi.bury.point.mapper.BpcBuryPointMapper;
import com.deepexi.bury.point.service.BpcBuryPointService;
import com.deepexi.util.BeanPowerHelper;
import com.deepexi.util.pageHelper.PageBean;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
@Service(version = "${demo.service.version}")
public class BpcBuryPointServiceImpl implements BpcBuryPointService {

    private static final Logger logger = LoggerFactory.getLogger(BpcBuryPointServiceImpl.class);

    @Resource
    private BpcBuryPointMapper bpcBuryPointMapper;

    @Autowired
    private AppRuntimeEnv appRuntimeEnv;

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
        int result = bpcBuryPointMapper.insert(BeanPowerHelper.mapPartOverrider(eo,BpcBuryPoint.class));
        return result > 0;
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

}