package com.example.trello.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiResponseCardEnum implements ApiResponseEnum {

    CARD_SAVE_OK(HttpStatus.OK, "카드가 등록되었습니다."),
    CARD_UPDATE_OK(HttpStatus.OK, "카드 수정이 완료 됐습니다."),
    CARD_NOT_FOUND(HttpStatus.NOT_FOUND, "카드가 존재하지 않습니다")
    ;

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    ApiResponseCardEnum(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
        code = httpStatus.value();
    }
}
