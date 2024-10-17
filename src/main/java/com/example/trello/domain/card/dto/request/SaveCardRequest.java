package com.example.trello.domain.card.dto.request;

import lombok.Getter;

@Getter
public class SaveCardRequest {
    private String title;
    private String content;
}