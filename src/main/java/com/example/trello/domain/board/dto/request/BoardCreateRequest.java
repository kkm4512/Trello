package com.example.trello.domain.board.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardCreateRequest {
    private String title;
    private String backgroundColor;
}
