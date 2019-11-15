package com.deepexi.bury.point.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Author: 白猛
 * @Date: 2019/11/15 12:41
 */

@Component
public class Test implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.profiles.active}")
    private String profile;

    @Override
    public void run(String... args) throws Exception {
        logger.info("环境参数：{}", profile);
    }
}
