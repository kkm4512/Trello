package com.example.trello.domain.list.entity;

import com.example.trello.common.entity.Timestamped;
import com.example.trello.domain.board.entity.Board;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board_list")
@NoArgsConstructor
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
    private Integer orderNum;

    @Builder
    public BoardList(User user, Board board, String title, Integer orderNum) {
        this.user = user;
        this.board = board;
        this.title = title;
        this.orderNum = orderNum;
    }

    public void updateTitle(String newTitle) {
        this.title = newTitle != null ? newTitle : title;
    }

    public void updateOrderNum(Integer order) {
        this.orderNum = order != null ? order : orderNum;
    }

}
