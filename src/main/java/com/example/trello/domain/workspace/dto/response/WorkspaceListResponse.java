package com.example.trello.domain.workspace.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceListResponse {
    private Long id;
    private String title;
    private String description;
    private String memberRole;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
