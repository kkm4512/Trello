package com.example.trello.common.aop;

import com.example.trello.domain.member.dto.request.MemberCreateRequest;
import com.example.trello.domain.slack.SlackService;
import com.example.trello.domain.user.dto.AuthUser;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.repository.UserRepository;
import com.example.trello.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Aspect
@Slf4j(topic = "AspectModule")
@RequiredArgsConstructor
public class AspectModule {
    private final SlackService slackService;
    private final UserRepository userRepository;
    private final static String SLACK_NOTI_ALL_ROOM = "C07R9380LQ7";

    /**
     * 특정 사용자가 워크스페이스 안에 추가되었을때 slack 알람이 추가되게 하는 AOP
     * 전체 공지방에 알람이 나간다
     */
    @Pointcut("@annotation(com.example.trello.common.annotation.MemberAddSlack)")
    private void memberAddPointCut() {}
    @Around("memberAddPointCut()")
    public Object adviceMemberAdd(ProceedingJoinPoint pjp) throws Throwable {
        Object result = pjp.proceed();
        MemberCreateRequest memberCreateRequest = (MemberCreateRequest) getArg(pjp,1);
        User user = userRepository.findById(memberCreateRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        slackService.sendMessage(
                SLACK_NOTI_ALL_ROOM,
                user.getEmail() + " 님을 멤버로 추가 하였습니다"
                );
        return result;
    }

    /**
     * 카드의 내용이 변경 되었을때,
     */
    @Pointcut("@annotation(com.example.trello.common.annotation.CardChangeSlack)")
    private void cardChangePointCut() {}
    @Around("cardChangePointCut()")
    public Object adviceCardChange(ProceedingJoinPoint pjp) throws Throwable {
        Object result = pjp.proceed();
        AuthUser authUser = getAuthUser();
        String channel = slackService.getIdByEmail(authUser.getEmail());
        slackService.sendMessage(
                channel,
                "나의 카드가 변경 되었습니다"
                );
        return result;
    }


    /**
     * 자신의 카드에 댓글이 달렸 을때
     */
    @Pointcut("@annotation(com.example.trello.common.annotation.CommentAddSlack)")
    private void commentPointCut(){}
    @Around("commentPointCut()")
    public Object adviceCommentAdd(ProceedingJoinPoint pjp) throws Throwable {
        Object result = pjp.proceed();
        AuthUser authUser = getAuthUser();
        String channel = slackService.getIdByEmail(authUser.getEmail());
        slackService.sendMessage(
                channel,
                "나의 카드에 댓글이 작성되었습니다"
        );
        return result;
    }



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

    // SecurityContextHolder에 있는 AuthUser 값 빼오기
    private AuthUser getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthUser authUser;
        Object principal = authentication.getPrincipal();
        authUser = ((AuthUser) principal);
        return authUser;
    }

    private Object getArg(ProceedingJoinPoint pjp,int order) {
        return pjp.getArgs()[order];
    }
}
