package com.example.trello.domain.list.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ListRequestDto {

    @NotBlank
    private String title;

    @Min(value = 0, message = "리스트 순서는 1 이상이어야 합니다.")
    private Integer titleOrder;

}
