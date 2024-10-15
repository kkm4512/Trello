package com.example.trello.domain.member.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberCreateResponse {
    private Long id;
    private Long workspaceId;
    private Long userId;
    private String email;
    private String memberRole;


}