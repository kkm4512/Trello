package com.example.trello.domain.list.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ListRequestDto {

    @NotBlank
    private String name;
    private Integer order;

}
