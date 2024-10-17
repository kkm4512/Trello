package com.example.trello.domain.test;

import com.example.trello.common.exception.TestException;
import com.example.trello.common.response.ApiResponse;
import com.example.trello.common.response.ApiResponseTestEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Test", description = "테스트 API")
@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;

    @Operation(summary = "테스트 API", description = "테스트 API")
    @GetMapping
    public String test(){
        return "사랑합니다 여러분 !";
    }

    // ApiResponse 테스트 예제
    @Operation(summary = "ApiResponse", description = "Test 예제")
    @GetMapping("/test1")
    public ResponseEntity<ApiResponse<String>> test1() {
        ApiResponse<String> apiResponse = testService.test();
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    // GlobalException Test 예제
    @Operation(summary = "GlobalException", description = "Test 예제")
    @GetMapping("/test1/test2")
    public ResponseEntity<ApiResponse<Void>> test2() {
        try {
            throw new TestException(ApiResponseTestEnum.TEST_FAIL);
        } catch (Exception e) {
            throw e;
        }
    }
}
