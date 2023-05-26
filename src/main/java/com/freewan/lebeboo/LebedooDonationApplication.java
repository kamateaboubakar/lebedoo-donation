package com.freewan.lebeboo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties(StorageProperties.class)
public class LebedooDonationApplication {

    public static void main(String[] args) {
        SpringApplication.run(LebedooDonationApplication.class, args);
    }

}
