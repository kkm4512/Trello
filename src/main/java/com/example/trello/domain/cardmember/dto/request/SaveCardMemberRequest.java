package com.example.trello.domain.cardmember.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class SaveCardMemberRequest {
    private List<Long> memberId;
}
