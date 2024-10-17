package com.example.trello.domain.card.dto.response;

import com.example.trello.domain.card.entity.Card;
import lombok.Getter;

@Getter
public class PutCardResponse {
    private final Long id;
    private final String title;
    private final String content;

    public PutCardResponse(Card card) {
        this.id = card.getId();
        this.title = card.getTitle();
        this.content = card.getContent();
    }
}