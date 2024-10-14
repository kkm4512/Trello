package com.example.trello.common.response;

import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class ApiResponse<T> {
    private final int code;
    private final String message;
    private T data;

    // 데이터, 상태코드, 메시지 반환 생성자 (ApiResponseEnumImpl)
    public ApiResponse(ApiResponseEnum apiResponseEnum) {
        this.code = apiResponseEnum.getCode();
        this.message = apiResponseEnum.getMessage();
    }

    // 데이터, 상태코드, 메시지 반환 생성자 (ApiResponseEnumImpl)
    public ApiResponse(ApiResponseEnum apiResponseEnum, T data) {
        this.code = apiResponseEnum.getCode();
        this.message = apiResponseEnum.getMessage();
        this.data = data;
    }

    // 데이터, 상태코드, 메시지 반환 생성자 (data,code,message)
    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 데이터, 상태코드, 메세지만 반환시 사용 (서비스 -> 컨트롤러)
    public static <T> ApiResponse<T> of(ApiResponseEnum apiResponseEnum, T data) {
        return new ApiResponse<>(apiResponseEnum,data);
    }

    // 데이터, 상태코드, 메세지만 반환시 사용 (컨트롤러 -> 클라이언트)
    public static <T> ResponseEntity<ApiResponse<T>> of(ApiResponse<T> apiResponse) {
        return ResponseEntity.status(apiResponse.getCode())
                .body(new ApiResponse<>(apiResponse.getCode(),apiResponse.getMessage(),apiResponse.getData()));
    }
}
