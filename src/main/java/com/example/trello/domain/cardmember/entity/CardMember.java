package com.example.trello.domain.cardmember.entity;

import com.example.trello.common.entity.Timestamped;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.member.entity.Member;
import com.example.trello.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "card_member")
@Getter
@NoArgsConstructor
public class CardMember extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    public CardMember(Card card, Member member) {
        this.card = card;
        this.member = member;
    }
}
