package com.example.trello.domain.card.dto.response;

import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.cardmember.dto.MemberInfo;
import com.example.trello.domain.comment.dto.CardCommentInfo;
import com.example.trello.domain.comment.entity.Comment;
import lombok.Getter;
import org.hibernate.annotations.Comments;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GetCardResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final List<MemberInfo> members;
    private final List<CardCommentInfo> comments;

    public GetCardResponse(Card card,List<MemberInfo> members, List<CardCommentInfo> comments) {
        this.id = card.getId();
        this.title = card.getTitle();
        this.content = card.getContent();
        this.createdAt = card.getCreatedAt();
        this.updatedAt = card.getUpdatedAt();
        this.members = members;
        this.comments = comments;
    }
}