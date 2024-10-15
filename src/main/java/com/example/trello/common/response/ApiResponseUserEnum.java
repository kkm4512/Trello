package com.example.trello.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiResponseUserEnum implements ApiResponseEnum {

    // 404
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자 검색에 실패하였습니다"),
    USER_SIGNUP_OK(HttpStatus.OK, "사용자 생성완료"),
    USER_LOGIN_OK(HttpStatus.OK, "로그인 완료"),
    USER_PASSWORD_OK(HttpStatus.OK, "비밀번호 변경 완료"),
    USER_DELETE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "사용자을 삭제하는데 실패하였습니다");


    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    ApiResponseUserEnum(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
        code = httpStatus.value();
    }
}
