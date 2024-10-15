package com.example.trello.domain.board.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponse {
    private Long id;
    private String title;
    private String backgroundColor;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
