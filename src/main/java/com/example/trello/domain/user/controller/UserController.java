package com.example.trello.domain.user.controller;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.domain.user.dto.UserRequestDto;
import com.example.trello.domain.user.dto.UserResponseDto;
import com.example.trello.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDto>> signup(@RequestBody UserRequestDto userRequest){
        ApiResponse<UserResponseDto> apiResponse = userService.signup(userRequest);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody UserRequestDto userRequest, HttpServletResponse res) {
        ApiResponse<String> apiResponse = userService.login(userRequest, res);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
