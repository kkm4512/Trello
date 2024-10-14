package com.example.trello.domain.user.service;

import com.example.trello.domain.user.dto.UserRequestDto;
import com.example.trello.domain.user.dto.UserResponseDto;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.entity.UserRoleEnum;
import com.example.trello.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class UserService {
    private final UserRepository userRepository;


    public UserResponseDto signup(UserRequestDto userRequest) {
        UserRoleEnum role = UserRoleEnum.USER;

        User user = new User(userRequest,role);
        User savedUser = userRepository.save(user);
        return new UserResponseDto(savedUser);
    }
}
