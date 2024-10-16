package com.example.trello.domain.comment.dto;

import com.example.trello.domain.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CardCommentInfo {
    private final Long id;
    private final String comment;
    private final Long userId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CardCommentInfo(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.userId = comment.getUser().getId();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }
}
