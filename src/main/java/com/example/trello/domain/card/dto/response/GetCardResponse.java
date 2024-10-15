package com.example.trello.domain.card.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetCardResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
