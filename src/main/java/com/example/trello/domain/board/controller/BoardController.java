package com.example.trello.domain.board.controller;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.domain.board.dto.request.BoardRequest;
import com.example.trello.domain.board.dto.response.BoardResponse;
import com.example.trello.domain.board.dto.response.BoardWithListsResponse;
import com.example.trello.domain.board.service.BoardService;
import com.example.trello.domain.user.dto.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Board", description = "이정현")
@RestController
@RequestMapping("/workspaces/{workspaceId}/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "보드 생성", description = "워크스페이스에 새로운 보드를 생성합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<BoardResponse>> createBoard(
            @PathVariable Long workspaceId,
            @AuthenticationPrincipal AuthUser authUser,
            @Valid @RequestBody BoardRequest request) {
        ApiResponse<BoardResponse> response = boardService.createBoard(workspaceId, authUser.getId(), request);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Operation(summary = "보드 수정", description = "워크스페이스의 보드를 수정합니다.")
    @PutMapping("/{boardId}")
    public ResponseEntity<ApiResponse<BoardResponse>> updateBoard(
            @PathVariable Long workspaceId,
            @PathVariable Long boardId,
            @AuthenticationPrincipal AuthUser authUser,
            @Valid @RequestBody BoardRequest request) {
        ApiResponse<BoardResponse> response = boardService.updateBoard(workspaceId, boardId, authUser.getId(), request);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Operation(summary = "보드 목록 조회", description = "워크스페이스의 모든 보드를 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<BoardResponse>>> getBoards(
            @PathVariable Long workspaceId,
            @AuthenticationPrincipal AuthUser authUser) {
        ApiResponse<List<BoardResponse>> response = boardService.getBoards(workspaceId, authUser.getId());
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Operation(summary = "보드 단건 조회", description = "워크스페이스의 보드 및 관련 리스트와 카드를 조회합니다.")
    @GetMapping("/{boardId}")
    public ResponseEntity<ApiResponse<BoardWithListsResponse>> getBoardWithLists(
            @PathVariable Long workspaceId,
            @PathVariable Long boardId,
            @AuthenticationPrincipal AuthUser authUser) {
        ApiResponse<BoardWithListsResponse> response = boardService.getBoardWithLists(workspaceId, boardId, authUser.getId());
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Operation(summary = "보드 삭제", description = "워크스페이스의 보드를 삭제합니다.")
    @DeleteMapping("/{boardId}")
    public ResponseEntity<ApiResponse<Void>> deleteBoard(
            @PathVariable Long workspaceId,
            @PathVariable Long boardId,
            @AuthenticationPrincipal AuthUser authUser) {
        ApiResponse<Void> response = boardService.deleteBoard(workspaceId, boardId, authUser.getId());
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
