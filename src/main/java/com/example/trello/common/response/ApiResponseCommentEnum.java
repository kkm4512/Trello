package com.example.trello.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiResponseCommentEnum implements ApiResponseEnum{
    // 200
    COMMENT_SAVE_OK(HttpStatus.OK, "댓글 등록 성공"),
    COMMENT_UPDATE_OK(HttpStatus.OK, "댓글 수정 성공"),
    COMMENT_DELETE_OK(HttpStatus.OK, "댓글 삭제 성공"),

    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글이 없습니다.");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    ApiResponseCommentEnum(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
        code = httpStatus.value();
    }
}
