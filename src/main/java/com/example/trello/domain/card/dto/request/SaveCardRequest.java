package com.example.trello.domain.card.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class SaveCardRequest {
    private String title;
    private String content;
    private List<Long> member;
}
