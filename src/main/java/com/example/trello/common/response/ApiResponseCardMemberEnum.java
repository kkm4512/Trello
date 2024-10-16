package com.example.trello.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiResponseCardMemberEnum implements ApiResponseEnum{
    // 200
    CARD_MEMBER_SAVE_OK(HttpStatus.OK, "카드 담당자 등록 성공"),
    CARD_MEMBER_DELETE_OK(HttpStatus.OK, "카드 담당자 삭제 성공");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    ApiResponseCardMemberEnum(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
        code = httpStatus.value();
    }
}
