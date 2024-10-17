package com.example.trello.domain.board.service;

import com.example.trello.common.exception.BoardException;
import com.example.trello.common.response.ApiResponse;
import com.example.trello.common.response.ApiResponseBoardEnum;
import com.example.trello.domain.board.dto.request.BoardRequest;
import com.example.trello.domain.board.dto.response.BoardResponse;
import com.example.trello.domain.board.dto.response.BoardWithListsResponse;
import com.example.trello.domain.board.entity.Board;
import com.example.trello.domain.board.repository.BoardRepository;
import com.example.trello.domain.board.validation.BoardValidator;
import com.example.trello.domain.workspace.entity.Workspace;
import com.example.trello.domain.workspace.repository.WorkspaceRepository;
import com.example.trello.domain.member.repository.MemberRepository;
import com.example.trello.domain.board.dto.response.BoardListResponse;
import com.example.trello.domain.board.dto.response.CardResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final WorkspaceRepository workspaceRepository;
    private final MemberRepository memberRepository;
    private final BoardValidator boardValidator;

    // 보드 생성
    @Transactional
    @Override
    public ApiResponse<BoardResponse> createBoard(Long workspaceId, Long memberId, BoardRequest request) {

        boardValidator.validateMemberInWorkspace(memberId, workspaceId);

        boardValidator.validateAdminOrMemberRole(memberId, workspaceId);

        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new BoardException(ApiResponseBoardEnum.WORKSPACE_NOT_FOUND));

        Board board = new Board();
        board.setTitle(request.getTitle());
        board.setBackgroundColor(request.getBackgroundColor());
        board.setWorkspace(workspace);
        boardRepository.save(board);

        BoardResponse response = buildBoardResponse(board);
        return ApiResponse.of(ApiResponseBoardEnum.BOARD_CREATE_SUCCESS, response);
    }

    // 보드 수정
    @Transactional
    @Override
    public ApiResponse<BoardResponse> updateBoard(Long workspaceId, Long boardId, Long memberId, BoardRequest request) {

        boardValidator.validateMemberInWorkspace(memberId, workspaceId);

        boardValidator.validateAdminOrMemberRole(memberId, workspaceId);

        Board board = boardRepository.findByIdAndWorkspaceId(boardId, workspaceId)
                .orElseThrow(() -> new BoardException(ApiResponseBoardEnum.BOARD_NOT_FOUND));

        board.setTitle(request.getTitle());
        board.setBackgroundColor(request.getBackgroundColor());
        boardRepository.save(board);

        BoardResponse response = buildBoardResponse(board);
        return ApiResponse.of(ApiResponseBoardEnum.BOARD_UPDATE_SUCCESS, response);
    }

    // 보드 조회
    @Transactional(readOnly = true)
    @Override
    public ApiResponse<List<BoardResponse>> getBoards(Long workspaceId, Long memberId) {

        boardValidator.validateMemberInWorkspace(memberId, workspaceId);

        List<Board> boards = boardRepository.findAllByWorkspaceId(workspaceId);
        List<BoardResponse> responses = boards.stream()
                .map(this::buildBoardResponse)
                .collect(Collectors.toList());

        return ApiResponse.of(ApiResponseBoardEnum.BOARD_READ_SUCCESS, responses);
    }

    // 보드 조회 (보드와 리스트)
    @Transactional(readOnly = true)
    @Override
    public ApiResponse<BoardWithListsResponse> getBoardWithLists(Long workspaceId, Long boardId, Long memberId) {

        boardValidator.validateMemberInWorkspace(memberId, workspaceId);

        Board board = boardRepository.findByIdAndWorkspaceId(boardId, workspaceId)
                .orElseThrow(() -> new BoardException(ApiResponseBoardEnum.BOARD_NOT_FOUND));

        BoardWithListsResponse response = buildBoardWithListsResponse(board);
        return ApiResponse.of(ApiResponseBoardEnum.BOARD_READ_SUCCESS, response);
    }

    // 보드 삭제
    @Transactional
    @Override
    public ApiResponse<Void> deleteBoard(Long workspaceId, Long boardId, Long memberId) {

        boardValidator.validateMemberInWorkspace(memberId, workspaceId);

        boardValidator.validateAdminOrMemberRole(memberId, workspaceId);

        Board board = boardRepository.findByIdAndWorkspaceId(boardId, workspaceId)
                .orElseThrow(() -> new BoardException(ApiResponseBoardEnum.BOARD_NOT_FOUND));

        boardRepository.delete(board);
        return ApiResponse.of(ApiResponseBoardEnum.BOARD_DELETE_SUCCESS, null);
    }

    // 보드 응답 생성 메서드
    private BoardResponse buildBoardResponse(Board board) {
        return BoardResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .backgroundColor(board.getBackgroundColor())
                .createdAt(board.getCreatedAt())
                .updatedAt(board.getUpdatedAt())
                .build();
    }

    // 보드와 리스트 응답 생성 메서드
    private BoardWithListsResponse buildBoardWithListsResponse(Board board) {
        return BoardWithListsResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .backgroundColor(board.getBackgroundColor())
                .createdAt(board.getCreatedAt())
                .updatedAt(board.getUpdatedAt())
                .boardLists(board.getBoardLists().stream()
                        .map(list -> BoardListResponse.builder()
                                .id(list.getId())
                                .title(list.getTitle())
                                .cards(list.getCards().stream()
                                        .map(card -> CardResponse.builder()
                                                .id(card.getId())
                                                .title(card.getTitle())
                                                .content(card.getContent())
                                                .createdAt(card.getCreatedAt())
                                                .updatedAt(card.getUpdatedAt())
                                                .build())
                                        .collect(Collectors.toList()))
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
