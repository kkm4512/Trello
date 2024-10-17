package com.example.trello.domain.cardmember.dto;

import com.example.trello.domain.cardmember.entity.CardMember;
import lombok.Getter;

@Getter
public class MemberInfo {
    private final Long id;
    private final String email;

    public MemberInfo(CardMember cardMember) {
        this.id = cardMember.getUser().getId();
        this.email = cardMember.getUser().getEmail();
    }

    public MemberInfo(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
