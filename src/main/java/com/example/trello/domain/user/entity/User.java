package com.example.trello.domain.user.entity;

import com.example.trello.common.entity.Timestamped;
import com.example.trello.domain.user.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SoftDelete;

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

    private String email;
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "deleted")
    private boolean deleted = false;


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
