package com.example.trello.domain.card.entity;

import com.example.trello.common.entity.Timestamped;
import com.example.trello.domain.list.entity.BoardList;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @JoinColumn(name = "board_list_id")
    private BoardList list;
}
