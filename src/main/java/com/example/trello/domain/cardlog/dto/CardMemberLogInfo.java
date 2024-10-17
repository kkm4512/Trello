package com.example.trello.domain.cardlog.dto;

import com.example.trello.domain.cardlog.enums.MemberChangeStatus;
import lombok.Getter;

@Getter
public class CardMemberLogInfo {
    private String member_Email;
    private MemberChangeStatus status;
}
