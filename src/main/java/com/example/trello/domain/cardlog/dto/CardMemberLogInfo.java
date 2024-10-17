package com.example.trello.domain.cardlog.dto;

import com.example.trello.domain.cardlog.entity.CardLog;
import com.example.trello.domain.cardlog.enums.MemberChangeStatus;
import lombok.Getter;

@Getter
public class CardMemberLogInfo {
    private final String member_Email;
    private final MemberChangeStatus status;

    public CardMemberLogInfo(CardLog cardMemberLogs) {
        this.member_Email = cardMemberLogs.getMember_email();
        this.status = cardMemberLogs.getStatus();
    }
}
