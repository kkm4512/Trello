package com.example.trello.domain.list.controller;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.domain.list.dto.ListRequestDto;
import com.example.trello.domain.list.dto.ListResponseDto;
import com.example.trello.domain.list.service.ListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board-list")
public class ListController {

    private final ListService listService;

    /**
     * Board의 List 생성
     */
    @PostMapping
    public ResponseEntity<ApiResponse> createBoardList(@RequestBody ListRequestDto requestDto) {
        ApiResponse<ListResponseDto> apiResponse = listService.createList(requestDto);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

}
