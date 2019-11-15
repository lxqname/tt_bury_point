package com.deepexi.bury.point.aop;

import com.deepexi.bury.point.config.MyAsyncConfigurer;
import com.deepexi.bury.point.service.impl.BpcBuryPointServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

/**
 * @Author: 白猛
 * @Date: 2019/11/15 12:41
 */

@Component
public class Test implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.profiles.active}")
    private String profile;

    private static Executor executor = new MyAsyncConfigurer().getAsyncExecutor();


    @Autowired
    BpcBuryPointServiceImpl bpcBuryPointService;


    @Override
    public void run(String... args) throws Exception {

//        bpcBuryPointService.reSendFailedMessage();

    }
}
