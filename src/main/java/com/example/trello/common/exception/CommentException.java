package com.example.trello.common.exception;

import com.example.trello.common.response.ApiResponseEnum;

public class CommentException extends BaseException{
    public CommentException(ApiResponseEnum apiResponseEnum) {
        super(apiResponseEnum);
    }
}
