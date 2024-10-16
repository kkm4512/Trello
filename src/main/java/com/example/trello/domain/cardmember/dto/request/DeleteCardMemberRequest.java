package com.example.trello.domain.cardmember.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class DeleteCardMemberRequest {
    private List<Long> memberId;
}
