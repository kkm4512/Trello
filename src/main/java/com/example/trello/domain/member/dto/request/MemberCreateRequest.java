package com.example.trello.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberCreateRequest {

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String email;

}
