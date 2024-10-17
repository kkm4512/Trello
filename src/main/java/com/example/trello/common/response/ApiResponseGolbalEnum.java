package com.example.trello.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiResponseGolbalEnum implements ApiResponseEnum {

    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 메서드입니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    ApiResponseGolbalEnum(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.code = httpStatus.value();
        this.message = message;
    }

}
