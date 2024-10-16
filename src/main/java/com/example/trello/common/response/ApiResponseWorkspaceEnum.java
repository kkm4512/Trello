package com.example.trello.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiResponseWorkspaceEnum implements ApiResponseEnum {

    // 성공 응답 200
    WORKSPACE_READ_SUCCESS(HttpStatus.OK, "워크스페이스 조회에 성공하였습니다."),
    WORKSPACE_CREATE_SUCCESS(HttpStatus.CREATED, "워크스페이스 생성에 성공하였습니다."),
    WORKSPACE_UPDATE_SUCCESS(HttpStatus.OK, "워크스페이스 수정에 성공하였습니다."),
    WORKSPACE_DELETE_SUCCESS(HttpStatus.NO_CONTENT, "워크스페이스 삭제에 성공하였습니다."),

    // 권한 오류 응답 403
    WORKSPACE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    WORKSPACE_CREATE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "워크스페이스 생성 권한이 없습니다."),

    // 리소스 없음 오류 응답 404
    WORKSPACE_NOT_FOUND(HttpStatus.NOT_FOUND, "워크스페이스를 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    ApiResponseWorkspaceEnum(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.code = httpStatus.value();
        this.message = message;
    }

}
