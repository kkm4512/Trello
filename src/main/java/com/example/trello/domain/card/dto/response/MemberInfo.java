package com.example.trello.domain.card.dto.response;

import lombok.Getter;

@Getter
public class MemberInfo {
    private Long id;
    private String email;

    public MemberInfo(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
