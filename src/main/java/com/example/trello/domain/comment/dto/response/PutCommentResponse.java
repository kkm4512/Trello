package com.example.trello.domain.comment.dto.response;

import lombok.Getter;

@Getter
public class PutCommentResponse {
    private Long id;
    private Long card_id;
    private String comment;
}
