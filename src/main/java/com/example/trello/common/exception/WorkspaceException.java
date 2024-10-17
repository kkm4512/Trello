package com.example.trello.common.exception;

import com.example.trello.common.response.ApiResponseEnum;

public class WorkspaceException extends BaseException {
    public WorkspaceException(ApiResponseEnum apiResponseEnum) { super(apiResponseEnum); }
}
