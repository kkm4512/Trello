package com.example.trello.domain.user.dto;

import com.example.trello.domain.user.entity.UserRoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserRequestDto {
    private  Long id;

    @Email
    private String email;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[\\p{Punct}])[A-Za-z\\d\\p{Punct}]{8,20}$",
            message = "Password must be 8-20 characters long and include at least one letter, one number, and one special character.")
    private String password;

    private UserRoleEnum role;
}
