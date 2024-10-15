package com.example.trello.domain.board.controller;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.domain.board.dto.request.BoardCreateRequest;
import com.example.trello.domain.board.dto.request.BoardUpdateRequest;
import com.example.trello.domain.board.dto.response.BoardResponse;
import com.example.trello.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workspaces/{workspaceId}/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<ApiResponse<BoardResponse>> createBoard(
            @PathVariable Long workspaceId,
            @RequestBody BoardCreateRequest request) {
        ApiResponse<BoardResponse> response = boardService.createBoard(workspaceId, request);
        return ApiResponse.of(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BoardResponse>>> getBoards(
            @PathVariable Long workspaceId) {
        ApiResponse<List<BoardResponse>> response = boardService.getBoards(workspaceId);
        return ApiResponse.of(response);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<ApiResponse<BoardResponse>> getBoard(
            @PathVariable Long workspaceId,
            @PathVariable Long boardId) {
        ApiResponse<BoardResponse> response = boardService.getBoard(workspaceId, boardId);
        return ApiResponse.of(response);
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<ApiResponse<BoardResponse>> updateBoard(
            @PathVariable Long workspaceId,
            @PathVariable Long boardId,
            @RequestBody BoardUpdateRequest request) {
        ApiResponse<BoardResponse> response = boardService.updateBoard(workspaceId, boardId, request);
        return ApiResponse.of(response);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<ApiResponse<Void>> deleteBoard(
            @PathVariable Long workspaceId,
            @PathVariable Long boardId) {
        ApiResponse<Void> response = boardService.deleteBoard(workspaceId, boardId);
        return ApiResponse.of(response);
    }
}