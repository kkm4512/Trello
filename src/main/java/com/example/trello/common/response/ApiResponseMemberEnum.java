package com.example.trello.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiResponseMemberEnum implements ApiResponseEnum {

        MEMBER_READ_SUCCESS(HttpStatus.OK, "멤버 조회에 성공하였습니다."),
        MEMBER_CREATE_SUCCESS(HttpStatus.CREATED, "멤버 생성에 성공하였습니다."),
        MEMBER_UPDATE_SUCCESS(HttpStatus.OK, "멤버 역할 수정에 성공하였습니다."),
        MEMBER_DELETE_SUCCESS(HttpStatus.NO_CONTENT, "멤버 삭제에 성공하였습니다."),

        MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "멤버를 찾을 수 없습니다."),
        WORKSPACE_NOT_FOUND(HttpStatus.NOT_FOUND, "워크스페이스를 찾을 수 없습니다."),
        USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 이메일의 사용자를 찾을 수 없습니다"),

        WORKSPACE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "해당 워크스페이스에 접근할 수 없습니다."),
        WORKSPACE_ADMIN_REQUIRED(HttpStatus.FORBIDDEN, "권한이 부족합니다."),

        MEMBER_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 해당 워크스페이스에 존재하는 멤버입니다."),

        // 요청 데이터 오류 응답 400
        MEMBER_ROLE_INVALID(HttpStatus.BAD_REQUEST, "유효하지 않은 멤버 역할입니다."),
        CANNOT_CHANGE_OWN_ROLE(HttpStatus.BAD_REQUEST, "자신의 역할은 변경할 수 없습니다."),
        CANNOT_DELETE_OWN_MEMBER(HttpStatus.BAD_REQUEST, "본인을 삭제할 수 없습니다."),
        INVALID_EMAIL(HttpStatus.BAD_REQUEST, "유효하지 않은 이메일입니다.");

        private final HttpStatus httpStatus;
        private final int code;
        private final String message;

        ApiResponseMemberEnum(HttpStatus httpStatus, String message) {
                this.httpStatus = httpStatus;
                this.code = httpStatus.value();
                this.message = message;
        }

}

