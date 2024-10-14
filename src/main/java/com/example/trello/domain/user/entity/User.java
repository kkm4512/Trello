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
@SoftDelete(columnName = "deleted")
@Table(name ="users")
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;

    private String password;

    private UserRoleEnum role;

    public User(UserRequestDto userRequest, UserRoleEnum role) {
        this.email = userRequest.getEmail();
        this.password = userRequest.getPassword();
        this.role = role;
    }
}
