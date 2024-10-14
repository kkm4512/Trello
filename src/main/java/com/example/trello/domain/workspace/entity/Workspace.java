package com.example.trello.domain.workspace.entity;

import com.example.trello.domain.member.entity.Member;
import com.example.trello.common.entity.Timestamped;
import com.example.trello.domain.board.entity.Board;
import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "workspace")
public class Workspace extends Timestamped {

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 255)
    private String description;

    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Member> members = new ArrayList<>();

}
