package com.example.trello.common.config;

import com.example.trello.common.aop.AspectModule;
import com.example.trello.domain.slack.SlackService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AspectConfig {
    private final SlackService slackService;

    @Bean
    public AspectModule aspectModule() {
        return new AspectModule(slackService);
    }
}
