package com.example.trello.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiResponseFileEnum implements ApiResponseEnum {

    // 200
    FILE_OK(HttpStatus.OK, "파일 작업 요청에 성공 하였습니다"),

    // 400
    FILE_UN_SUPPORT_FORMAT(HttpStatus.BAD_REQUEST, "파일 형식은 jpg,png,pdf,csv 만 가능합니다"),
    FILE_BIG_SIZE(HttpStatus.BAD_REQUEST, "파일의 크기는 5MB를 넘을 수 없습니다"),
    FILE_CANT_ADD_READ_ROLE(HttpStatus.BAD_REQUEST, "읽기 전용 권한 유저는, 파일을 추가 할 수 없습니다"),

    // 404
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "파일 조회에 실패 하였습니다"),

    // 500
    FILE_IO_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예상치 못한 오류로 파일을 읽지 못했습니다"),
    FILE_DELETE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "파일을 삭제하는데 실패 하였습니다"),
    DIRECTORY_CREATE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "디렉토리 생성에 실패 하였습니다"),
    FILE_COPY_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "파일 복사에 실패 하였습니다")
    ;

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    ApiResponseFileEnum(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
        code = httpStatus.value();
    }
}
