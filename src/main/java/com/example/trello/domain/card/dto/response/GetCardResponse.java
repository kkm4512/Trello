package com.example.trello.domain.card.dto.response;

import com.example.trello.domain.card.entity.Card;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetCardResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public GetCardResponse(Card card) {
        this.id = card.getId();
        this.title = card.getTitle();
        this.content = card.getContent();
        this.createdAt = card.getCreatedAt();
        this.updatedAt = card.getUpdatedAt();
    }
}
