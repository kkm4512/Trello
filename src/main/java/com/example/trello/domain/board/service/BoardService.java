package com.example.trello.domain.board.service;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.domain.board.dto.request.BoardRequest;
import com.example.trello.domain.board.dto.response.BoardResponse;
import com.example.trello.domain.board.dto.response.BoardWithListsResponse;

import java.util.List;

public interface BoardService {

    ApiResponse<BoardResponse> createBoard(Long workspaceId, Long memberId, BoardRequest request);

    ApiResponse<BoardResponse> updateBoard(Long workspaceId, Long boardId, Long memberId, BoardRequest request);

    ApiResponse<List<BoardResponse>> getBoards(Long workspaceId, Long memberId);

    ApiResponse<BoardWithListsResponse> getBoardWithLists(Long workspaceId, Long boardId, Long memberId);

    ApiResponse<Void> deleteBoard(Long workspaceId, Long boardId, Long memberId);
}
