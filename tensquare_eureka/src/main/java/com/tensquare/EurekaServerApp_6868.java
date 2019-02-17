package com.tensquare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author LiangDong.
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApp_6868 {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApp_6868.class, args);
    }
}
