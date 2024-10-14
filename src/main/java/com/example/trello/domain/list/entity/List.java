package com.example.trello.domain.list.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.smartcardio.Card;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board_list")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BoardList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @OneToMany(mappedBy = "list", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Card> cards = new ArrayList<>();

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;

    @Column(nullable = false, unique = true)
    private String title;

    @Transient
    private List<String> titleList = new ArrayList<>();

    @Builder
    public BoardList(Board board, List<Card> cards, String title) {
        this.board = board;
        this.cards = cards;
        this.title = title;
    }

    // 리스트 제목 순서 변화 반영
    public void saveListOrder(Integer order, String title) {
        titleList.add(order - 1, title);
    }
}
