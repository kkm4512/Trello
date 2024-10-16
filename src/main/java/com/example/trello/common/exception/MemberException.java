package com.example.trello.common.exception;

import com.example.trello.common.response.ApiResponseEnum;

public class MemberException extends BaseException {
    public MemberException(ApiResponseEnum apiResponseEnum) { super(apiResponseEnum); }
}
