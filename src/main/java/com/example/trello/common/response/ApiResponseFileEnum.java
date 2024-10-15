package com.example.trello.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiResponseFileEnum implements ApiResponseEnum {

    // 404
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "파일 조회에 실패 하였습니다"),

    // 500
    FILE_IO_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예상치 못한 오류로 파일을 읽지 못했습니다"),
    FILE_DELETE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "파일을 삭제하는데 실패 하였습니다"),
    ;

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    ApiResponseFileEnum(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
        code = httpStatus.value();
    }
}
