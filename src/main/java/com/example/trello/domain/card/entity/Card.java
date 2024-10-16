package com.example.trello.domain.card.entity;

import com.example.trello.common.entity.Timestamped;
import com.example.trello.domain.board.entity.Board;
import com.example.trello.domain.card.dto.request.PutCardRequest;
import com.example.trello.domain.card.dto.request.SaveCardRequest;
import com.example.trello.domain.list.entity.BoardList;
import com.example.trello.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "card")
@Getter
@NoArgsConstructor
public class Card extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_list_id")
    private BoardList list;

//    @OneToMany(mappedBy = "card", cascade = CascadeType.REMOVE)
//    private List<member> cardManagers;

    public Card(SaveCardRequest request, BoardList list) {
        this.title = request.getTitle();
        this.content = request.getContent();
        this.list = list;
    }

    public void update(PutCardRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
    }
}
