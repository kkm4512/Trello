package com.example.trello.domain.user.entity;

import com.example.trello.common.entity.Timestamped;
import com.example.trello.domain.comment.entity.Comment;
import com.example.trello.domain.member.entity.Member;
import com.example.trello.domain.user.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SoftDelete;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="users")
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "deleted")
    private boolean deleted = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Member> members = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();


    public User(UserRequestDto userRequest, UserRole role, String password) {
        this.email = userRequest.getEmail();
        this.password = password;
        this.role = role;
    }


    public void changePassword(String password) {
        this.password = password;
    }

    public void delete() {
        this.deleted = true;
    }
}
