package com.example.trello.common.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                // 연결시간 5초 까지만
                .setConnectTimeout(Duration.ofSeconds(5))
                // 읽기 5초 까지만
                .setReadTimeout(Duration.ofSeconds(5))
                .build();
    }
}
