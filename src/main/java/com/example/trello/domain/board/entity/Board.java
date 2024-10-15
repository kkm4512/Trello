package com.example.trello.domain.board.entity;


import com.example.trello.domain.workspace.entity.Workspace;
import com.example.trello.domain.list.entity.BoardList;
import com.example.trello.common.entity.Timestamped;
import com.example.trello.domain.user.entity.User;
import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "board")
public class Board extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 7)
    private String backgroundColor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id", nullable = false)
    private Workspace workspace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardList> boardLists = new ArrayList<>();

}
