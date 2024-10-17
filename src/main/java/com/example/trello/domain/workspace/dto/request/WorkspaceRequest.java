package com.example.trello.domain.workspace.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceRequest {

    @NotBlank(message = "워크스페이스 제목은 필수 입력입니다.")
    private String title;

    @Size(max = 50, message = "50자 이내로 입력해주세요.")
    private String description;

}