package com.example.trello.domain.member.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberUpdateResponse {
    private Long id;
    private Long workspaceId;
    private Long userId;
    private String memberRole;
    private LocalDateTime updatedAt;
}