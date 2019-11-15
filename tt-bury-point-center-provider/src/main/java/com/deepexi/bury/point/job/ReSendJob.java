package com.deepexi.bury.point.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.deepexi.bury.point.service.BpcBuryPointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: 白猛
 * @Date: 2019/11/15 17:41
 */
@Component
public class ReSendJob implements SimpleJob {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BpcBuryPointService bpcBuryPointService;

    @Override
    public void execute(ShardingContext shardingContext) {
        logger.info("重新发送消息任务：启动！");
        bpcBuryPointService.reSendFailedMessage();
    }
}
