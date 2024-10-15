package com.example.trello.domain.list.controller;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.domain.list.dto.request.ListCreateRequestDto;
import com.example.trello.domain.list.dto.request.ListUpdateDto;
import com.example.trello.domain.list.dto.response.ListResponseDto;
import com.example.trello.domain.list.service.ListService;
import com.example.trello.domain.user.dto.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ListController {

    private final ListService listService;

    /**
     * Board의 List 생성
     */
    @PostMapping("workspaces/{workspaceId}/boards/{boardId}/lists")
    public ResponseEntity<ApiResponse> createBoardList(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long workspaceId,
            @PathVariable Long boardId,
            @RequestBody ListCreateRequestDto requestDto
    ) {
        ApiResponse<ListResponseDto> apiResponse = listService.createList(authUser.getId(), workspaceId, boardId, requestDto);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @PutMapping("workspaces/{workspaceId}/boards/{boardId}/lists/{listId}")
    public ResponseEntity<ApiResponse> updateBoardList(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long workspaceId,
            @PathVariable Long boardId,
            @PathVariable Long listId,
            @RequestBody ListUpdateDto requestDto
    ) {
        ApiResponse<ListResponseDto> apiResponse = listService.updateList(authUser.getId(), workspaceId, boardId, listId, requestDto);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    /**
     * Board의 List 삭제
     */
    @DeleteMapping("workspaces/{workspaceId}/boards/{boardId}/lists/{listId}")
    public ResponseEntity<ApiResponse> deleteBoardList(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long workspaceId,
            @PathVariable Long boardId,
            @PathVariable Long listId
    ) {
        ApiResponse<Void> apiResponse = listService.deleteList(authUser.getId(), workspaceId, boardId, listId);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }






}
