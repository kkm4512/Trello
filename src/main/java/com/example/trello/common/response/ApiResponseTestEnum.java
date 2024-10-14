package com.example.trello.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiResponseTestEnum implements ApiResponseEnum {
    TEST_OK(HttpStatus.OK, "테스트 굳"),

    // 201
    TEST_SUCCESS(HttpStatus.CREATED, "테스트 생성 완료"),

    // 400
    TEST_FAIL(HttpStatus.BAD_REQUEST,"테스트 실패"),

    //401
    TEST_UNAUTHORIZED(HttpStatus.BAD_REQUEST,"테스트 인증 불가"),

    //404
    TEST_NOT_FOUND(HttpStatus.NOT_FOUND,"테스트를 찾을 수 없습니다"),

    // 409
    TEST_DUPLICATE_CHECK(HttpStatus.CONFLICT, "테스트 중복 발생");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    ApiResponseTestEnum(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
        code = httpStatus.value();
    }
}
