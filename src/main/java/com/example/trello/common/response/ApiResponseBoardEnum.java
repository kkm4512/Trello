package com.example.trello.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiResponseBoardEnum implements ApiResponseEnum {

            // 200
            BOARD_READ_SUCCESS(HttpStatus.OK, "보드 조회에 성공 하였습니다"),
            BOARD_CREATE_SUCCESS(HttpStatus.OK, "보드 생성에 성공 하였습니다"),
            BOARD_UPDATE_SUCCESS(HttpStatus.OK, "보드 수정에 성공 하였습니다"),
            BOARD_DELETE_SUCCESS(HttpStatus.OK, "보드 삭제에 성공 하였습니다"),

            // 400
            BOARD_READ_FAIL(HttpStatus.BAD_REQUEST, "보드 조회에 실패 하였습니다"),
            BOARD_CREATE_FAIL(HttpStatus.BAD_REQUEST, "보드 생성에 실패 하였습니다"),
            BOARD_UPDATE_FAIL(HttpStatus.BAD_REQUEST, "보드 수정에 실패 하였습니다"),
            BOARD_DELETE_FAIL(HttpStatus.BAD_REQUEST, "보드 삭제에 실패 하였습니다"),

            // 404
            BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "보드를 찾을 수 없습니다"),

            // 500
            BOARD_IO_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예상치 못한 오류로 보드를 불러오지 못했습니다"),
            BOARD_DELETE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "보드를 삭제하는데 실패 하였습니다");

            private final HttpStatus httpStatus;
            private final int code;
            private final String message;

            ApiResponseBoardEnum(HttpStatus httpStatus, String message) {
                    this.httpStatus = httpStatus;
                    this.message = message;
                    code = httpStatus.value();
            }
}
