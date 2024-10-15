package com.example.trello.domain.user.dto;

import com.example.trello.domain.user.entity.UserRole;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
public class AuthUser {
    private final Long id;
    private final Collection<? extends GrantedAuthority> authorities;
    private final String email;

    public AuthUser(Long id, UserRole role, String email){
        this. id = id;
        this.authorities = List.of(new SimpleGrantedAuthority((role.name())));
        this.email = email;
    }
}