package com.example.trello.domain.list.dto;

import com.example.trello.domain.list.entity.BoardList;
import com.example.trello.domain.list.entity.List;
import lombok.Getter;

@Getter
public class ListResponseDto {

    private Long listId;
    private Long boardId;
    private String title;
    private int cardCount;

    public static ListResponseDto of(BoardList list) {
        return new ListRequestDto(
                list.getBoard().getId(),
                list.getTitle(),
                list.getId(),
                list.getCards().size()
        );

    }

}
