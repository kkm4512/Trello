package com.example.trello.domain.board.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardUpdateRequest {
    private String title;
    private String backgroundColor;
}
