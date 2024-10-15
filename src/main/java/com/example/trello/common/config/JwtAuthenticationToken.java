package com.example.trello.common.config;

import com.example.trello.domain.user.dto.AuthUser;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private final AuthUser authUser;
    public JwtAuthenticationToken(AuthUser authUser) {
        super(authUser.getAuthorities());
        this.authUser = authUser;
        setAuthenticated(true);
    }
    @Override
    public Object getCredentials() {
        return null;
    }
    @Override
    public Object getPrincipal() {
        return authUser;
    }
}

