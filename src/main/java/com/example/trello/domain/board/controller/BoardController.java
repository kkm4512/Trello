package com.example.trello.domain.board.controller;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.domain.board.dto.request.BoardCreateRequest;
import com.example.trello.domain.board.dto.request.BoardUpdateRequest;
import com.example.trello.domain.board.dto.response.BoardResponse;
import com.example.trello.domain.board.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Board", description = "보드 API")
@RestController
@RequestMapping("/workspaces/{workspaceId}/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "보드 생성", description = "워크스페이스에 보드를 생성합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<BoardResponse>> createBoard(
            @PathVariable Long workspaceId,
            @RequestBody BoardCreateRequest request) {
        ApiResponse<BoardResponse> response = boardService.createBoard(workspaceId, request);
        return ApiResponse.of(response);
    }

    @Operation(summary = "보드 목록 조회", description = "워크스페이스의 보드 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<BoardResponse>>> getBoards(
            @PathVariable Long workspaceId) {
        ApiResponse<List<BoardResponse>> response = boardService.getBoards(workspaceId);
        return ApiResponse.of(response);
    }

    @Operation(summary = "보드 단건 조회", description = "워크스페이스의 보드를 조회합니다.")
    @GetMapping("/{boardId}")
    public ResponseEntity<ApiResponse<BoardResponse>> getBoard(
            @PathVariable Long workspaceId,
            @PathVariable Long boardId) {
        ApiResponse<BoardResponse> response = boardService.getBoard(workspaceId, boardId);
        return ApiResponse.of(response);
    }

    @Operation(summary = "보드 수정", description = "워크스페이스의 보드를 수정합니다.")
    @PutMapping("/{boardId}")
    public ResponseEntity<ApiResponse<BoardResponse>> updateBoard(
            @PathVariable Long workspaceId,
            @PathVariable Long boardId,
            @RequestBody BoardUpdateRequest request) {
        ApiResponse<BoardResponse> response = boardService.updateBoard(workspaceId, boardId, request);
        return ApiResponse.of(response);
    }

    @Operation(summary = "보드 삭제", description = "워크스페이스의 보드를 삭제합니다.")
    @DeleteMapping("/{boardId}")
    public ResponseEntity<ApiResponse<Void>> deleteBoard(
            @PathVariable Long workspaceId,
            @PathVariable Long boardId) {
        ApiResponse<Void> response = boardService.deleteBoard(workspaceId, boardId);
        return ApiResponse.of(response);
    }
}