package com.example.trello.domain.card.dto.response;

import com.example.trello.domain.card.entity.Card;
import lombok.Getter;

import java.util.List;

@Getter
public class SaveCardResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final List<MemberInfo> member;

    public SaveCardResponse(Card card, List<MemberInfo> registeredMembers) {
        this.id = card.getId();
        this.title = card.getTitle();
        this.content = card.getContent();
        this.member = registeredMembers;
    }
}
