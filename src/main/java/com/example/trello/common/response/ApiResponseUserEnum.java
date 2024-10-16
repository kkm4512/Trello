package com.example.trello.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiResponseUserEnum implements ApiResponseEnum {

    // 201
    USER_SIGNUP_OK(HttpStatus.CREATED, "사용자 생성완료"),

    //200
    USER_LOGIN_OK(HttpStatus.OK, "로그인 완료"),
    USER_PASSWORD_OK(HttpStatus.OK, "비밀번호 변경 완료"),
    USER_DELETE_OK(HttpStatus.OK, "회원 탈퇴완료"),

    //403
    USER_ROLE_IS_READ_ONLY(HttpStatus.FORBIDDEN,"유저의 권한은 읽기 전용 입니다"),

    //404
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."),


    //409
    USER_DELETED_ERROR(HttpStatus.CONFLICT, "이미 탈퇴한 회원입니다"),
    USER_DUPLICATE_EMAIL_ERROR(HttpStatus.CONFLICT, "이미 가입된 이메일 입니다"),

    //401
    USER_ADMINTOKEN_ERROR(HttpStatus.UNAUTHORIZED,"관리자 가입 인증암호가 일치하지 않아 등록이 불가능합니다"),
    USER_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "유저 정보가 일치 하지 않습니다"),
    USER_PASSWORD_ERROR(HttpStatus.UNAUTHORIZED,"잘못된 비밀 번호를 입력하셨습니다"),

    //500
    USER_DELETE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "사용자을 삭제하는데 실패하였습니다"),
    ;



    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    ApiResponseUserEnum(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
        code = httpStatus.value();
    }
}
