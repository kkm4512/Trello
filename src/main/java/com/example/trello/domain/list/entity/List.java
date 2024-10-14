package com.example.trello.domain.list.entity;

import com.example.trello.common.entity.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.smartcardio.Card;
import java.util.ArrayList;

@Entity
@Table(name = "board_list")
@NoArgsConstructor
@Getter
public class List extends Timestamped {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @OneToMany(mappedBy = "list", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Card> cards = new ArrayList<>();

    @Column(nullable = false, unique = true)
    private String title;

    @Column(name = "list_order")
    private Integer order;
}
