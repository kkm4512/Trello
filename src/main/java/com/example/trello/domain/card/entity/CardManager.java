package com.example.trello.domain.card.entity;

import com.example.trello.common.entity.Timestamped;
import com.example.trello.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "card_manager")
@Getter
@NoArgsConstructor
public class CardManager extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    public CardManager(User member, Card card) {
        this.user = member;
        this.card = card;
    }
}
