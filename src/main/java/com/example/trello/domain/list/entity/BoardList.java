package com.example.trello.domain.list.entity;

import com.example.trello.common.entity.Timestamped;
import com.example.trello.domain.board.entity.Board;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board_list")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BoardList extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @OneToMany(mappedBy = "list", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Card> cards = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, unique = true)
    private String title;

    @Column
    private Integer order;

    @Builder
    public BoardList(Board board, String title, Integer order) {
        this.board = board;
        this.title = title;
        this.order = order;
    }
}
