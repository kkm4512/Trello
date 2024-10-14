package com.example.trello.common.exception;

import com.example.trello.common.response.ApiResponseEnum;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private ApiResponseEnum apiResponseEnum;

    public BaseException(ApiResponseEnum apiResponseEnum) {
        this.apiResponseEnum = apiResponseEnum;
    }
}
