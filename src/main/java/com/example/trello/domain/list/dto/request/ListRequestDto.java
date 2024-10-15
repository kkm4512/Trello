package com.example.trello.domain.list.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ListRequestDto {
    @NotBlank
    private String title;
    private Integer orderNum;
}
