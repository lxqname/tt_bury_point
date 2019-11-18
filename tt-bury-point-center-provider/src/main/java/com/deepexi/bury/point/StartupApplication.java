package com.deepexi.bury.point;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StartupApplication implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${spring.profiles.active}")
    private String profile;

    public static void main(String[] args) {
        SpringApplication.run(StartupApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("环境参数：{}", profile);
    }
}
