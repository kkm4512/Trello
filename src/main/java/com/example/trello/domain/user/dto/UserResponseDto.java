package com.example.trello.domain.user.dto;

import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.entity.UserRole;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponseDto {
    private Long id;
    private String email;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    private UserRole role;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.createAt = user.getCreatedAt();
        this.modifiedAt = user.getModifiedAt();
        this.role = user.getRole();
    }
}
