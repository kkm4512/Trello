package com.example.trello.domain.cardlog.dto.response;

import com.example.trello.domain.cardlog.dto.CardMemberLogInfo;
import lombok.Getter;

import java.util.List;

@Getter
public class MemberLogResponse {
    private final List<CardMemberLogInfo> cardMemberLogInfo;

    public MemberLogResponse(List<CardMemberLogInfo> cardMemberLogInfo) {
        this.cardMemberLogInfo = cardMemberLogInfo;
    }
}
