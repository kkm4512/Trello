package com.example.trello.domain.workspace.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceUpdateRequest {
    private String title;
    private String description;
}
