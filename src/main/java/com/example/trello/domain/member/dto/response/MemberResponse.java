// MemberResponse.java (DTO 응답)
package com.example.trello.domain.member.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponse {
    private Long id;
    private Long workspaceId;
    private Long userId;
    private String memberRole;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
