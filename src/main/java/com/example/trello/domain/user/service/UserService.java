package com.example.trello.domain.user.service;

import com.example.trello.common.config.JwtUtil;
import com.example.trello.common.response.ApiResponse;
import com.example.trello.common.response.ApiResponseEnum;
import com.example.trello.common.response.ApiResponseUserEnum;
import com.example.trello.domain.user.dto.AuthUser;
import com.example.trello.domain.user.dto.ChangePasswordRequestDto;
import com.example.trello.domain.user.dto.UserRequestDto;
import com.example.trello.domain.user.dto.UserResponseDto;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.entity.UserRole;
import com.example.trello.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Value("${admin.token}")
    private String ADMIN_TOKEN;


    public ApiResponse<UserResponseDto> signup(@Valid UserRequestDto userRequest) {
        //암호화
        String password = passwordEncoder.encode(userRequest.getPassword());
        String email = userRequest.getEmail();
        ApiResponseEnum apiResponseEnum = ApiResponseUserEnum.USER_SIGNUP_OK;
        UserRole role = UserRole.USER;

        //이메일 중복 검증
        if (userRepository.findByEmail(email).isPresent()){
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        if(userRepository.findDeletedEmail(email).isPresent()){
            throw new IllegalArgumentException("이미 탈퇴한 이메일입니다.");
        }

        //관리자 권한 검증
        if (userRequest.isAdmin()) {
            if (!ADMIN_TOKEN.equals(userRequest.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 일치하지 않아 등록이 불가능합니다.");
            }
            role = UserRole.ADMIN;
        }

        User user = new User(userRequest,role, password);
        User savedUser = userRepository.save(user);
        ApiResponse<UserResponseDto> apiResponse = new ApiResponse<>(apiResponseEnum, new UserResponseDto(savedUser));
        return apiResponse;
    }

    public ApiResponse<String> login(UserRequestDto userRequest, HttpServletResponse res) {
        String email = userRequest.getEmail();
        String password = userRequest.getPassword();
        ApiResponseEnum apiResponseEnum = ApiResponseUserEnum.USER_LOGIN_OK;

        User user = (User) userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));

        if(userRepository.findDeletedEmail(email).isPresent()){
            throw new IllegalArgumentException("이미 탈퇴한 이메일입니다.");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀 번호를 입력하셨습니다.");
        }
        String token = jwtUtil.createToken(user.getId(), user.getEmail(), String.valueOf(user.getRole()));
        jwtUtil.addJwtToCookie(token, res);
        ApiResponse<String> apiResponse = new ApiResponse<>(apiResponseEnum, token);
        return apiResponse;
    }

    public ApiResponse<String> changePassword(Long userId, ChangePasswordRequestDto changePasswordRequest, AuthUser authUser) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
        ApiResponseEnum apiResponseEnum = ApiResponseUserEnum.USER_PASSWORD_OK;

        if(!userId.equals(authUser.getId())) {
            throw new IllegalArgumentException("유저 정보가 일치 하지 않습니다.");
        }
        if(!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("현제 비밀번호가 일치하지 않습니다.");
        }
        System.out.println(changePasswordRequest.getNewPassword());
        String password = passwordEncoder.encode(changePasswordRequest.getNewPassword());
        user.changePassword(password);
        userRepository.save(user);

        ApiResponse<String> apiResponse = new ApiResponse<>(apiResponseEnum, "비밀번호 변경 완료");

        return apiResponse;
    }
}
