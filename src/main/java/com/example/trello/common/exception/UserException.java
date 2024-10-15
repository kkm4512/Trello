package com.example.trello.common.exception;

import com.example.trello.common.response.ApiResponseEnum;

public class UserException extends BaseException {
    public UserException(ApiResponseEnum apiResponseEnum) {
        super(apiResponseEnum);
    }
}
