package com.example.trello.domain.board.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardWithListsResponse {
    private Long id;
    private String title;
    private String backgroundColor;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<BoardListResponse> boardLists;
}
