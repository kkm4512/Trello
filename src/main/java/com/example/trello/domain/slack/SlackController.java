package com.example.trello.domain.slack;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/slack")
public class SlackController {
    private final SlackService slackService;

    @GetMapping
    public void slackTest1(){
        String name = "testName";
        slackService.memberAddSlackTest(name);
    }

    @GetMapping("/test")
    public void slackTest2(){
        slackService.cardChangeSlackTest();
    }

    @GetMapping("/test/test")
    public void slackTest3(){
        slackService.commentAddSlackTest();
    }
}
