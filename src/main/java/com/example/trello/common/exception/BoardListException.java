package com.example.trello.common.exception;

import com.example.trello.common.response.ApiResponseEnum;

public class BoardListException extends BaseException{
    public BoardListException(ApiResponseEnum apiResponseEnum) {
        super(apiResponseEnum);
    }
}
