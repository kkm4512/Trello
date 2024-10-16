package com.example.trello.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiResponseListEnum implements ApiResponseEnum {

    LIST_CREATE_OK(HttpStatus.CREATED, "리스트가 생성되었습니다"),
    LIST_UPDATE_OK(HttpStatus.OK, "리스트가 수정되었습니다"),
    LIST_DELETED_OK(HttpStatus.OK, "리스트가 삭제되었습니다");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    ApiResponseListEnum(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
        code = httpStatus.value();
    }

}
