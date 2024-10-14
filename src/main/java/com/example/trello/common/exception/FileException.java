package com.example.trello.common.exception;

import com.example.trello.common.response.ApiResponseEnum;

public class FileException extends BaseException {
    public FileException(ApiResponseEnum apiResponseEnum) {
        super(apiResponseEnum);
    }
}
