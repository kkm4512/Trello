package com.example.trello.domain.list.dto.response;

import com.example.trello.domain.list.entity.BoardList;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ListResponseDto {

    private Long listId;
    private Long boardId;
    private String title;
    private Integer order;

    public static ListResponseDto of(BoardList list) {
        return new ListResponseDto(
                list.getBoard().getId(),
                list.getId(),
                list.getTitle(),
                list.getOrder()
        );

    }

}
