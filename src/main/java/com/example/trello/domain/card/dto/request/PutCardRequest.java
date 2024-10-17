package com.example.trello.domain.card.dto.request;

import lombok.Getter;

@Getter
public class PutCardRequest {
    private String title;
    private String content;
}