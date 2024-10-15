package com.example.trello.domain.board.service;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.common.response.ApiResponseBoardEnum;
import com.example.trello.domain.board.dto.request.BoardCreateRequest;
import com.example.trello.domain.board.dto.request.BoardUpdateRequest;
import com.example.trello.domain.board.dto.response.BoardResponse;
import com.example.trello.domain.board.entity.Board;
import com.example.trello.domain.board.repository.BoardRepository;
import com.example.trello.domain.workspace.entity.Workspace;
import com.example.trello.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final WorkspaceRepository workspaceRepository;

    // 보드 생성
    public ApiResponse<BoardResponse> createBoard(Long workspaceId, BoardCreateRequest request) {
        try {
            Workspace workspace = workspaceRepository.findById(workspaceId)
                    .orElseThrow(() -> new IllegalArgumentException("Workspace not found"));

            Board board = new Board();
            board.setTitle(request.getTitle());
            board.setBackgroundColor(request.getBackgroundColor());
            board.setWorkspace(workspace);
            board.setCreatedAt(LocalDateTime.now());
            board.setUpdatedAt(LocalDateTime.now());
            boardRepository.save(board);

            BoardResponse response = new BoardResponse(
                    board.getId(),
                    board.getTitle(),
                    board.getBackgroundColor(),
                    board.getCreatedAt(),
                    board.getUpdatedAt());

            return ApiResponse.of(ApiResponseBoardEnum.BOARD_CREATE_SUCCESS, response);
        } catch (Exception e) {
            return ApiResponse.of(ApiResponseBoardEnum.BOARD_CREATE_FAIL, null);
        }
    }

    // 보드 목록 조회
    public ApiResponse<List<BoardResponse>> getBoards(Long workspaceId) {
        try {
            List<Board> boards = boardRepository.findAllByWorkspaceId(workspaceId);
            List<BoardResponse> response = boards.stream()
                    .map(board -> new BoardResponse(
                            board.getId(),
                            board.getTitle(),
                            board.getBackgroundColor(),
                            board.getCreatedAt(),
                            board.getUpdatedAt()))
                    .collect(Collectors.toList());
            return ApiResponse.of(ApiResponseBoardEnum.BOARD_READ_SUCCESS, response);
        } catch (Exception e) {
            return ApiResponse.of(ApiResponseBoardEnum.BOARD_READ_FAIL, null);
        }
    }

    // 보드 단건 조회
    public ApiResponse<BoardResponse> getBoard(Long workspaceId, Long boardId) {
        try {
            Board board = boardRepository.findByIdAndWorkspaceId(boardId, workspaceId)
                    .orElseThrow(() -> new IllegalArgumentException("Board not found"));

            BoardResponse response = new BoardResponse(
                    board.getId(),
                    board.getTitle(),
                    board.getBackgroundColor(),
                    board.getCreatedAt(),
                    board.getUpdatedAt());

            return ApiResponse.of(ApiResponseBoardEnum.BOARD_READ_SUCCESS, response);
        } catch (Exception e) {
            return ApiResponse.of(ApiResponseBoardEnum.BOARD_READ_FAIL, null);
        }
    }

    // 보드 수정
    public ApiResponse<BoardResponse> updateBoard(Long workspaceId, Long boardId, BoardUpdateRequest request) {
        try {
            Board board = boardRepository.findByIdAndWorkspaceId(boardId, workspaceId)
                    .orElseThrow(() -> new IllegalArgumentException("Board not found"));

            board.setTitle(request.getTitle());
            board.setBackgroundColor(request.getBackgroundColor());
            board.setUpdatedAt(LocalDateTime.now());
            boardRepository.save(board);

            BoardResponse response = new BoardResponse(
                    board.getId(),
                    board.getTitle(),
                    board.getBackgroundColor(),
                    board.getCreatedAt(),
                    board.getUpdatedAt());

            return ApiResponse.of(ApiResponseBoardEnum.BOARD_UPDATE_SUCCESS, response);
        } catch (Exception e) {
            return ApiResponse.of(ApiResponseBoardEnum.BOARD_UPDATE_FAIL, null);
        }
    }

    // 보드 삭제
    public ApiResponse<Void> deleteBoard(Long workspaceId, Long boardId) {
        try {
            Board board = boardRepository.findByIdAndWorkspaceId(boardId, workspaceId)
                    .orElseThrow(() -> new IllegalArgumentException(ApiResponseBoardEnum.BOARD_NOT_FOUND.getMessage()));
            boardRepository.delete(board);
            return ApiResponse.of(ApiResponseBoardEnum.BOARD_DELETE_SUCCESS, null);
        } catch (IllegalArgumentException e) {
            return ApiResponse.of(ApiResponseBoardEnum.BOARD_NOT_FOUND, null);
        } catch (Exception e) {
            return ApiResponse.of(ApiResponseBoardEnum.BOARD_DELETE_FAIL, null);
        }
    }
}