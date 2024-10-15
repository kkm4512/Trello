package com.example.trello.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiResponseMemberEnum implements ApiResponseEnum {

        // 200
        MEMBER_READ_SUCCESS(HttpStatus.OK, "멤버 조회에 성공 하였습니다"),
        MEMBER_CREATE_SUCCESS(HttpStatus.OK, "멤버 생성에 성공 하였습니다"),
        MEMBER_UPDATE_SUCCESS(HttpStatus.OK, "멤버 수정에 성공 하였습니다"),
        MEMBER_DELETE_SUCCESS(HttpStatus.OK, "멤버 삭제에 성공 하였습니다"),

        // 400
        MEMBER_READ_FAIL(HttpStatus.BAD_REQUEST, "멤버 조회에 실패 하였습니다"),
        MEMBER_CREATE_FAIL(HttpStatus.BAD_REQUEST, "멤버 생성에 실패 하였습니다"),
        MEMBER_UPDATE_FAIL(HttpStatus.BAD_REQUEST, "멤버 수정에 실패 하였습니다"),
        MEMBER_DELETE_FAIL(HttpStatus.BAD_REQUEST, "멤버 삭제에 실패 하였습니다"),

        // 404
        MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "멤버를 찾을 수 없습니다"),

        // 500
        MEMBER_IO_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예상치 못한 오류로 멤버를 불러오지 못했습니다"),
        MEMBER_DELETE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "멤버를 삭제하는데 실패 하였습니다");

        private final HttpStatus httpStatus;
        private final int code;
        private final String message;

        ApiResponseMemberEnum(HttpStatus httpStatus, String message) {
                this.httpStatus = httpStatus;
                this.message = message;
                code = httpStatus.value();
        }
}