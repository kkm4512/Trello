package com.example.trello.domain.member.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberUpdateRequest {
    private String memberRole;
}