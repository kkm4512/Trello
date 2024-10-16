package com.example.trello.domain.cardmember.dto;

import lombok.Getter;

@Getter
public class MemberInfo {
    private final Long id;
    private final String email;

    public MemberInfo(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
