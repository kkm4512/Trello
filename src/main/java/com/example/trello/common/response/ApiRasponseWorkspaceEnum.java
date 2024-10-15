package com.example.trello.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiRasponseWorkspaceEnum implements ApiResponseEnum {

            // 200
            WORKSPACE_READ_SUCCESS(HttpStatus.OK, "워크스페이스 조회에 성공 하였습니다"),
            WORKSPACE_CREATE_SUCCESS(HttpStatus.OK, "워크스페이스 생성에 성공 하였습니다"),
            WORKSPACE_UPDATE_SUCCESS(HttpStatus.OK, "워크스페이스 수정에 성공 하였습니다"),
            WORKSPACE_DELETE_SUCCESS(HttpStatus.OK, "워크스페이스 삭제에 성공 하였습니다"),

            // 400
            WORKSPACE_READ_FAIL(HttpStatus.BAD_REQUEST, "워크스페이스 조회에 실패 하였습니다"),
            WORKSPACE_CREATE_FAIL(HttpStatus.BAD_REQUEST, "워크스페이스 생성에 실패 하였습니다"),
            WORKSPACE_UPDATE_FAIL(HttpStatus.BAD_REQUEST, "워크스페이스 수정에 실패 하였습니다"),
            WORKSPACE_DELETE_FAIL(HttpStatus.BAD_REQUEST, "워크스페이스 삭제에 실패 하였습니다"),

            // 404
            WORKSPACE_NOT_FOUND(HttpStatus.NOT_FOUND, "워크스페이스를 찾을 수 없습니다"),

            // 500
            WORKSPACE_IO_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예상치 못한 오류로 워크스페이스를 불러오지 못했습니다"),
            WORKSPACE_DELETE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "워크스페이스를 삭제하는데 실패 하였습니다");

            private final HttpStatus httpStatus;
            private final int code;
            private final String message;

            ApiRasponseWorkspaceEnum(HttpStatus httpStatus, String message) {
                    this.httpStatus = httpStatus;
                    this.message = message;
                    code = httpStatus.value();
            }

}