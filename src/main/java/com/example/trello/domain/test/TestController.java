package com.example.trello.domain.test;

import com.example.trello.common.exception.TestException;
import com.example.trello.common.response.ApiResponse;
import com.example.trello.common.response.ApiResponseTestEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;

    // ApiResponse 테스트 예제
    @GetMapping("/test1")
    public ResponseEntity<ApiResponse<String>> test1() {
        ApiResponse<String> apiResponse = testService.test();
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    // GlobalException Test 예제
    @GetMapping("/test1/test2")
    public ResponseEntity<ApiResponse<Void>> test2() {
        try {
            throw new TestException(ApiResponseTestEnum.TEST_FAIL);
        } catch (Exception e) {
            throw e;
        }
    }
}
