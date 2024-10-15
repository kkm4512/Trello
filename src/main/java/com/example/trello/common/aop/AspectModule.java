package com.example.trello.common.aop;

import com.example.trello.domain.slack.SlackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
@Slf4j
@RequiredArgsConstructor
public class AspectModule {
    private final SlackService slackService;

    // TODO: 멤버 추가하는 메서드로 경로 변경해야함

    /**
     * 특정 사용자가 워크스페이스 안에 추가되었을때 slack 알람이 추가되게 하는 AOP
     * 전체 공지방에 알람이 나간다
     */
    @Pointcut("execution(* com.example.trello.domain.workspace.service.WorkspaceService..*(..))")
    private void memberAddPointCut() {}

    // TODO: 카드 변경하는 메서드로 경로 변경해야함

    /**
     * 카드의 내용이 변경 되었을때,
     */
    @Pointcut("execution(* com.example.trello.domain.card.service.CardService..*(..))")
    private void cardChangePointCut() {}

    // TODO: 댓글 달리는 메서드로 경로 변경해야함
    /**
     * 자신의 카드에 댓글이 달렸 을때
     */
    @Pointcut("execution(* com.example.trello.domain.card.service.CardService..*(..))")
    private void commentPointCut() {}

    // TODO: AOP Test Slack PointCut
    @Pointcut("@annotation(com.example.trello.common.annotation.Slack)")
    private void testSlack(){}

    @Around("testSlack()")
    public Object adviceTestSlack(ProceedingJoinPoint pjp) throws Throwable {
        Object result = pjp.proceed();
        String channel = slackService.getIdByEmail("nayoun340@gmail.com");
        slackService.sendMessage(channel, "Hello World!");
        return result;
    }
}
