package com.example.trello.domain.card.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchCardRequest {

    private Long cardId; // cardId로 검색
    private Long boardId; // cardId로 검색
    private String title;
    private String content;

    // (카드 생성 기간 검색)
    private LocalDateTime startAt; // 시작 기간
    private LocalDateTime endAt; // 끝 기간

}
