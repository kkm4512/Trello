package com.example.trello.domain.list.controller;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.domain.list.dto.request.ListCreateRequestDto;
import com.example.trello.domain.list.dto.response.ListResponseDto;
import com.example.trello.domain.list.service.ListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ListController {

    private final ListService listService;

    /**
     * Board의 List 생성
     */
    @PostMapping("/{{boardId}/list")
    public ResponseEntity<ApiResponse> createBoardList(@PathVariable Long boardId, @RequestBody ListCreateRequestDto requestDto) {
        ApiResponse<ListResponseDto> apiResponse = listService.createList(boardId, requestDto);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

}
