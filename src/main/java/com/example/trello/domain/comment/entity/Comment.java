package com.example.trello.domain.comment.entity;

import com.example.trello.common.entity.Timestamped;
import com.example.trello.domain.card.dto.request.SaveCardRequest;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.comment.dto.request.PutCommentRequest;
import com.example.trello.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    public Comment(SaveCardRequest request, Card card, User user) {
        this.comment = request.getContent();
        this.card = card;
        this.user = user;
    }

    public void update(PutCommentRequest request) {
        this.comment = request.getComment();
    }
}
