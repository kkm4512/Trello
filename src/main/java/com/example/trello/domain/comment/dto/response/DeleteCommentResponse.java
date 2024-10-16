package com.example.trello.domain.comment.dto.response;

import com.example.trello.domain.comment.entity.Comment;
import lombok.Getter;

@Getter
public class DeleteCommentResponse {
    private Long id;

    public DeleteCommentResponse(Comment comment) {
        this.id = comment.getId();
    }
}
