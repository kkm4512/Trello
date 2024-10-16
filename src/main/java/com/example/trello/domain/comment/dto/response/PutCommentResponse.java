package com.example.trello.domain.comment.dto.response;

import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.comment.entity.Comment;
import lombok.Getter;

@Getter
public class PutCommentResponse {
    private Long id;
    private Long card_id;
    private String comment;

    public PutCommentResponse(Comment comment, Card card) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this. card_id = card.getId();
    }
}
