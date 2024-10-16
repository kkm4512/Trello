package com.example.trello.common.exception;

import com.example.trello.common.response.ApiResponseEnum;

public class CardException extends BaseException {
    public CardException(ApiResponseEnum apiResponseEnum) {
        super(apiResponseEnum);
    }
}
