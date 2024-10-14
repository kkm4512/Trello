package com.example.trello.common.exception;

import com.example.trello.common.response.ApiResponseEnum;

public class TestException extends BaseException {
    public TestException(ApiResponseEnum apiResponseEnum) {
        super(apiResponseEnum);
    }
}
