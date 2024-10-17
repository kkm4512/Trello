package com.example.trello.common.config;

import com.example.trello.common.aop.AspectModule;
import com.example.trello.domain.member.repository.MemberRepository;
import com.example.trello.domain.slack.SlackService;
import com.example.trello.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AspectConfig {
    private final SlackService slackService;
    private final UserRepository userRepository;

    @Bean
    public AspectModule aspectModule() {
        return new AspectModule(slackService, userRepository);
    }
}
