package com.example.trello.domain.card.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class SaveCardResponse {
    private Long id;
    private String title;
    private String content;
    private List<Long> user;
}
