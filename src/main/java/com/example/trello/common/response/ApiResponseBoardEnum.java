package com.example.trello.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiResponseBoardEnum implements ApiResponseEnum {

    BOARD_READ_SUCCESS(HttpStatus.OK, "보드 조회에 성공하였습니다."),
    BOARD_CREATE_SUCCESS(HttpStatus.CREATED, "보드 생성에 성공하였습니다."),
    BOARD_UPDATE_SUCCESS(HttpStatus.OK, "보드 수정에 성공하였습니다."),
    BOARD_DELETE_SUCCESS(HttpStatus.NO_CONTENT, "보드 삭제에 성공하였습니다."),


    WORKSPACE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "권한이 없습니다."),

    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 보드를 찾을 수 없습니다."),
    WORKSPACE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 워크스페이스를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    ApiResponseBoardEnum(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.code = httpStatus.value();
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}