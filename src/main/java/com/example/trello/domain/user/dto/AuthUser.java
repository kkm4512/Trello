package com.example.trello.domain.user.dto;

import com.example.trello.domain.user.entity.UserRoleEnum;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthUser {
    private final Long id;
    private final UserRoleEnum role;
    private final String email;

    public AuthUser(Long id,UserRoleEnum role,String email){
        this. id = id;
        this.role = role;
        this.email = email;
    }
}