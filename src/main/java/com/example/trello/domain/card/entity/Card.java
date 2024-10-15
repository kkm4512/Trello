package com.example.trello.domain.card.entity;

import com.example.trello.common.entity.Timestamped;
import com.example.trello.domain.list.entity.List;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Entity
@Table(name = "card")
@Getter
@NoArgsConstructor
public class Card extends Timestamped {
    private String title;
    private String content;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "list_id")
    private List list;
}
