package com.yufu.yepshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author wang
 * @date 2022/1/5 23:15
 */
@SpringBootApplication
@EnableJpaAuditing
public class YepshopApplication {
    public static void main(String[] args) {
        SpringApplication.run(YepshopApplication.class, args);
    }
}
