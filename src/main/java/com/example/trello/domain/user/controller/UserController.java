package com.example.trello.domain.user.controller;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.domain.user.dto.AuthUser;
import com.example.trello.domain.user.dto.ChangePasswordRequestDto;
import com.example.trello.domain.user.dto.UserRequestDto;
import com.example.trello.domain.user.dto.UserResponseDto;
import com.example.trello.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "user", description = "김태현")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "회원가입을 합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDto>> signup(@RequestBody UserRequestDto userRequest){
        ApiResponse<UserResponseDto> apiResponse = userService.signup(userRequest);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @Operation(summary = "로그인", description = "로그인을 합니다.")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody UserRequestDto userRequest, HttpServletResponse res) {
        ApiResponse<String> apiResponse = userService.login(userRequest, res);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @Operation(summary = "로그아웃", description = "로그아웃을 합니다.")
    @PatchMapping("/{user_id}/change_password")
    public ResponseEntity<ApiResponse<String>> changePassword(@PathVariable Long user_id, @RequestBody ChangePasswordRequestDto changePasswordRequest, @AuthenticationPrincipal AuthUser authUser) {
        ApiResponse<String> response = userService.changePassword(user_id, changePasswordRequest, authUser);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Operation(summary = "회원탈퇴", description = "회원탈퇴를 합니다.")
    @DeleteMapping("/{user_id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Long user_id, @RequestBody UserRequestDto userRequest, @AuthenticationPrincipal AuthUser authUser) {
        ApiResponse<String> response = userService.deleteUser(user_id, userRequest, authUser);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
