package com.example.trello.domain.test;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.common.response.ApiResponseEnum;
import com.example.trello.common.response.ApiResponseTestEnum;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    // ApiResponse 테스트 예제
    public ApiResponse<String> test() {
        ApiResponseEnum apiResponseEnum = ApiResponseTestEnum.TEST_OK;
        ApiResponse<String> apiResponse = new ApiResponse<>(apiResponseEnum,"test 입니다");
        return apiResponse;
    }
}
