package com.example.trello.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiResponseCardEnum implements ApiResponseEnum {
    // 200
    CARD_SAVE_OK(HttpStatus.OK, "카드가 등록되었습니다."),
    CARD_UPDATE_OK(HttpStatus.OK, "카드 수정이 완료 됐습니다."),
    CARD_GET_OK(HttpStatus.OK, "카드를 불러왔습니다."),
    CARD_DELETE_OK(HttpStatus.OK, " 카드가 삭제되었습니다"),
    CARD_SEARCH_OK(HttpStatus.OK, "카드들이 검색되었습니다"),

    // 404
    CARD_NOT_FOUND(HttpStatus.NOT_FOUND, "카드가 존재하지 않습니다"),
    NOT_CARD_OWNER(HttpStatus.FORBIDDEN, "카드 담당자가 아닙니다.");


    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    ApiResponseCardEnum(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
        code = httpStatus.value();
    }
}
