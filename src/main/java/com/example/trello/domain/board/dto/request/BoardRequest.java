package com.example.trello.domain.board.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequest {
    private String title;
    private String backgroundColor;
}
