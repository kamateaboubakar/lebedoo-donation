package com.freewan.lebeboo.account;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class AccountClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor(@Value("${account.token}") String token) {
        return requestTemplate -> {
            String headerValue = "%s %s".formatted("Bearer", token);
            requestTemplate.header("Authorization", headerValue);
        };
    }
}
