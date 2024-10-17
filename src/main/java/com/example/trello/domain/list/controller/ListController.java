package com.example.trello.domain.list.controller;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.domain.list.dto.request.ListCreateRequestDto;
import com.example.trello.domain.list.dto.request.ListUpdateDto;
import com.example.trello.domain.list.dto.response.ListResponseDto;
import com.example.trello.domain.list.service.ListService;
import com.example.trello.domain.user.dto.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "List", description = "김도균")
@RestController
@RequiredArgsConstructor
public class ListController {

    private final ListService listService;

    /**
     * Board의 List 생성
     */
    @Operation(summary = "보드 리스트 생성", description = "보드에 새로운 리스트를 생성합니다.")
    @PostMapping("workspaces/{workspaceId}/boards/{boardId}/lists")
    public ResponseEntity<ApiResponse<ListResponseDto>> createBoardList(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long workspaceId,
            @PathVariable Long boardId,
            @RequestBody ListCreateRequestDto requestDto
    ) {
        ApiResponse<ListResponseDto> apiResponse = listService.createList(authUser.getId(), workspaceId, boardId, requestDto);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @Operation(summary = "보드 리스트 수정", description = "보드의 리스트를 수정합니다.")
    @PutMapping("workspaces/{workspaceId}/boards/{boardId}/lists/{listId}")
    public ResponseEntity<ApiResponse<ListResponseDto>> updateBoardList(
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
    @Operation
    @DeleteMapping("workspaces/{workspaceId}/boards/{boardId}/lists/{listId}")
    public ResponseEntity<ApiResponse<Void>> deleteBoardList(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long workspaceId,
            @PathVariable Long boardId,
            @PathVariable Long listId
    ) {
        ApiResponse<Void> apiResponse = listService.deleteList(authUser.getId(), workspaceId, boardId, listId);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
