package com.example.trello.domain.attachment.entity;

import com.example.trello.common.entity.Timestamped;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "attachment")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attachment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originFileName;
    private String path;
    private Long card_id;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;


}
