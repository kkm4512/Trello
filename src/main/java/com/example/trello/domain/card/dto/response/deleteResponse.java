package com.example.trello.domain.card.dto.response;

import com.example.trello.domain.card.entity.Card;
import lombok.Getter;

@Getter
public class deleteResponse {
    private final Long id;

    public deleteResponse(Card card) {
        this.id = card.getId();
    }
}