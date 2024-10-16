package com.example.trello.domain.card.dto.response;

import com.example.trello.domain.card.entity.Card;
import lombok.Getter;

@Getter
public class SaveCardResponse {
    private final Long id;
    private final String title;
    private final String content;

    public SaveCardResponse(Card card) {
        this.id = card.getId();
        this.title = card.getTitle();
        this.content = card.getContent();
    }
}
