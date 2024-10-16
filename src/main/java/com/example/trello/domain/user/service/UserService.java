package com.example.trello.domain.user.service;

import com.example.trello.common.config.JwtUtil;
import com.example.trello.common.exception.UserException;
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

        //탈퇴한 유저 검증
        if(userRepository.findDeletedEmail(email).isPresent()){
            throw new UserException(ApiResponseUserEnum.USER_DELETED_ERROR);
        }

        //이메일 중복 검증
        if (userRepository.findByEmail(email).isPresent()){
            throw new UserException(ApiResponseUserEnum.USER_DUPLICATE_EMAIL_ERROR);
        }

        //관리자 권한 검증
        if (userRequest.isAdmin()) {
            if (!ADMIN_TOKEN.equals(userRequest.getAdminToken())) {
                throw new UserException(ApiResponseUserEnum.USER_ADMINTOKEN_ERROR);
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

        User user = (User) userRepository.findByEmail(email).orElseThrow(() -> new UserException(ApiResponseUserEnum.USER_UNAUTHORIZED));
        //탈퇴한 유저 검증
        if(userRepository.findDeletedEmail(email).isPresent()){
            throw new UserException(ApiResponseUserEnum.USER_DELETED_ERROR);
        }
        //비밀번호 검증
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UserException(ApiResponseUserEnum.USER_PASSWORD_ERROR);
        }

        String token = jwtUtil.createToken(user.getId(), user.getEmail(), String.valueOf(user.getRole()));
        jwtUtil.addJwtToCookie(token, res);
        ApiResponse<String> apiResponse = new ApiResponse<>(apiResponseEnum, token);
        return apiResponse;
    }

    public ApiResponse<String> changePassword(Long userId, ChangePasswordRequestDto changePasswordRequest, AuthUser authUser) {
        User user = findUserById(userId);
        ApiResponseEnum apiResponseEnum = ApiResponseUserEnum.USER_PASSWORD_OK;

        //로그인 유저와 비밀번호 변경 유저 검증
        if(!userId.equals(authUser.getId())) {
            throw new UserException(ApiResponseUserEnum.USER_UNAUTHORIZED);
        }
        //비밀번호 검증
        if(!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new UserException(ApiResponseUserEnum.USER_PASSWORD_ERROR);
        }

        String password = passwordEncoder.encode(changePasswordRequest.getNewPassword());
        user.changePassword(password);
        userRepository.save(user);

        ApiResponse<String> apiResponse = new ApiResponse<>(apiResponseEnum, "비밀번호 변경 완료");
        return apiResponse;
    }

    public ApiResponse<String> deleteUser(Long userId, UserRequestDto userRequest, AuthUser authUser) {
        User user = findUserById(userId);
        ApiResponseEnum apiResponseEnum = ApiResponseUserEnum.USER_DELETE_OK;
        String password = userRequest.getPassword();

        //로그인 유저와 탈퇴시도 유저 검증
        if(!userId.equals(authUser.getId())) {
            throw new UserException(ApiResponseUserEnum.USER_UNAUTHORIZED);
        }
        //유저 비밀번호 검증
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new UserException(ApiResponseUserEnum.USER_PASSWORD_ERROR);
        }

        user.delete();
        userRepository.save(user);
        ApiResponse<String> apiResponse = new ApiResponse<>(apiResponseEnum, "회원탈퇴 완료");
        return apiResponse;
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserException(ApiResponseUserEnum.USER_NOT_FOUND));
    }
}
