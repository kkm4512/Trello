package com.example.trello.common.exception;

import com.example.trello.common.response.ApiResponseEnum;

public class BoardException extends BaseException {
    public BoardException(ApiResponseEnum apiResponseEnum) { super(apiResponseEnum); }
}
