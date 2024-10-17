package com.example.trello.domain.board.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardListResponse {
    private Long id;
    private String title;
    private List<CardResponse> cards;
}
