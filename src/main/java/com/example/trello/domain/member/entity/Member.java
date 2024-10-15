package com.example.trello.domain.member.entity;

import com.example.trello.domain.workspace.entity.Workspace;
import com.example.trello.common.entity.Timestamped;
//import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.member.enums.MemberRole;
import jakarta.persistence.*;

@Entity
@Table(name = "member")
public class Member extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id", nullable = false)
    private Workspace workspace;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MemberRole memberRole;

}
