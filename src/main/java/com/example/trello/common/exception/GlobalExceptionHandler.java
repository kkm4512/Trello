package com.example.trello.common.exception;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.common.response.ApiResponseEnum;
import com.example.trello.common.response.ApiResponseGolbalEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<?> baseException(BaseException e) {
        ApiResponseEnum apiResponseEnum = e.getApiResponseEnum();
        ApiResponse<Void> apiResponse = new ApiResponse<>(apiResponseEnum);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    // GET, POST, PUT, DELETE 등의 HTTP 메서드가 잘못된 경우
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Void>> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        ApiResponse<Void> apiResponse = new ApiResponse<>(ApiResponseGolbalEnum.METHOD_NOT_ALLOWED);
        return ApiResponse.of(apiResponse);
    }

    // @RequestParam, @PathVariable, @RequestHeader 등의 필수 파라미터가 누락된 경우
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Void>> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        ApiResponse<Void> apiResponse = new ApiResponse<>(ApiResponseGolbalEnum.BAD_REQUEST);
        return ApiResponse.of(apiResponse);
    }

    // @Valid 유효성 검사 실패 시 발생하는 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse(ApiResponseGolbalEnum.BAD_REQUEST.getMessage());
        ApiResponse<Void> apiResponse = new ApiResponse<>(ApiResponseGolbalEnum.BAD_REQUEST.getCode(), errorMessage, null);
        return ResponseEntity.badRequest().body(apiResponse);
    }

    /*
    // 그 외 모든 예외 처리 = 예상치 못한 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
        ApiResponse<Void> apiResponse = new ApiResponse<>(ApiResponseGolbalEnum.INTERNAL_SERVER_ERROR);
        return ApiResponse.of(apiResponse);
    }
    */

}