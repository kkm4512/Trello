package com.example.trello.domain.cardlog.entity;

import com.example.trello.common.entity.Timestamped;
import com.example.trello.domain.attachment.entity.Attachment;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.cardlog.enums.MemberChangeStatus;
import com.example.trello.domain.member.entity.Member;
import com.example.trello.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "card_log")
@Getter
@NoArgsConstructor
public class CardLog extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String member_email;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberChangeStatus status;


    public CardLog(Member user, Member member, Card card, MemberChangeStatus memberChangeStatus) {
        this.member_email = member.getUser().getEmail();
        this.user = user.getUser();
        this.card = card;
        this.status = memberChangeStatus;
    }
}
