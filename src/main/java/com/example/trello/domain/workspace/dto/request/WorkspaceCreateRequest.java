package com.example.trello.domain.workspace.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceCreateRequest {
    private String title;
    private String description;
}