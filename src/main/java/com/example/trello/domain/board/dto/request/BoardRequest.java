package com.example.trello.domain.board.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequest {

    @NotBlank(message = "보드 제목은 필수입니다.")
    private String title;

    @Size(max = 7, message = "보드 배경색은 7자리 이하로 입력해주세요.")
    private String backgroundColor;

}
