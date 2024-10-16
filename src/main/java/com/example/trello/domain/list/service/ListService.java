package com.example.trello.domain.list.service;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.common.response.ApiResponseEnum;
import com.example.trello.common.response.ApiResponseTestEnum;
import com.example.trello.domain.board.entity.Board;
import com.example.trello.domain.board.repository.BoardRepository;
import com.example.trello.domain.list.dto.request.ListCreateRequestDto;
import com.example.trello.domain.list.dto.request.ListUpdateDto;
import com.example.trello.domain.list.dto.response.ListResponseDto;
import com.example.trello.domain.list.entity.BoardList;
import com.example.trello.domain.list.repository.ListRepository;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.repository.UserRepository;
import com.example.trello.domain.workspace.entity.Workspace;
import com.example.trello.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ListService {

    private final ListRepository listRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final WorkspaceRepository workspaceRepository;

    /**
     * 보드 리스트 생성
     */
    public ApiResponse<ListResponseDto> createList(Long userId, Long workspaceId, Long boardId, ListCreateRequestDto requestDto) {
        // 검증 및 보드 가져오기
        Board board = validateUserWorkspaceBoard(userId, workspaceId, boardId);

        // 새로운 리스트 생성
        BoardList boardList = BoardList.builder()
                .board(board)
                .user(userRepository.findById(userId).get()) // 이미 검증된 유저
                .title(requestDto.getTitle())
                .orderNum(1) // 새 리스트는 맨 앞에 추가
                .build();

        // 기존 리스트들 순서 전부 + 1
        listRepository.findByBoardId(boardId).forEach(list -> list.updateOrderNum(list.getOrderNum() + 1));

        // 새 리스트 저장(더티체킹 있지만 명시적으로 표시함)
        listRepository.save(boardList);

        ListResponseDto responseData = ListResponseDto.of(boardList);
        return new ApiResponse<>(ApiResponseTestEnum.TEST_SUCCESS, responseData);
    }

    /**
     * 보드 리스트 제목 or 순서 수정
     */
    public ApiResponse<ListResponseDto> updateList(Long userId, Long workspaceId, Long boardId, Long listId, ListUpdateDto requestDto) {
        // 검증 및 보드 가져오기
        Board board = validateUserWorkspaceBoard(userId, workspaceId, boardId);

        // 수정할 리스트 가져오기
        BoardList boardList = listRepository.findById(listId)
                .orElseThrow(() -> new NoSuchElementException("리스트를 찾을 수 없습니다."));

        // 기존 순서와 새로운 순서 비교
        Integer existingOrderNum = boardList.getOrderNum();
        Integer newOrderNum = requestDto.getOrderNum() != null ? requestDto.getOrderNum() : existingOrderNum;

        if (!existingOrderNum.equals(newOrderNum)) { // 만약 리스트를 옮겨서 순서가 변한다면
            List<BoardList> existingLists = listRepository.findByBoardId(boardId);
            if (existingOrderNum < newOrderNum) { // 리스트를 뒤로 옮길 때
                existingLists.stream()
                        // 기존 순서보다 큰 리스트들만 순서를 조정
                        .filter(list -> list.getOrderNum() > existingOrderNum && list.getOrderNum() <= newOrderNum)
                        .forEach(list -> list.updateOrderNum(list.getOrderNum() - 1));
            } else {
                // 리스트를 앞으로 옮길 때
                existingLists.stream()
                        // 기존 순서보다 앞에 있는 리스트들 중, 새로운 위치로 이동할 리스트까지 순서 조정
                        .filter(list -> list.getOrderNum() < existingOrderNum && list.getOrderNum() >= newOrderNum) // 기존 미만 신규 이상 자리의 리스트
                        .forEach(list -> list.updateOrderNum(list.getOrderNum() + 1));
            }
            boardList.updateOrderNum(newOrderNum); //
        }

        boardList.updateTitle(requestDto.getTitle());
        ListResponseDto responseData = ListResponseDto.of(boardList);
        return new ApiResponse<>(ApiResponseTestEnum.TEST_SUCCESS, responseData);
    }

    /**
     * 보드 리스트 삭제
     */
    public ApiResponse<Void> deleteList(Long userId, Long workspaceId, Long boardId, Long listId) {
        // 검증 및 보드 가져오기
        Board board = validateUserWorkspaceBoard(userId, workspaceId, boardId);

        // 삭제할 리스트 가져오기
        BoardList boardList = listRepository.findById(listId)
                .orElseThrow(() -> new NoSuchElementException("리스트를 찾을 수 없습니다."));

        // 삭제할 리스트 순서 조정
        Integer deleteOrderNum = boardList.getOrderNum();
        adjustOrderAfterDelete(listRepository.findByBoardId(boardId), deleteOrderNum);

        // 리스트 삭제
        listRepository.delete(boardList);

        return new ApiResponse<>(ApiResponseTestEnum.TEST_SUCCESS, null);
    }

    /**
     * 유저, 워크스페이스, 보드 검증 공통 로직
     */
    private Board validateUserWorkspaceBoard(Long userId, Long workspaceId, Long boardId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("유저를 찾을 수 없습니다."));

        workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new NoSuchElementException("워크스페이스를 찾을 수 없습니다."));

        return boardRepository.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException("보드를 찾을 수 없습니다."));
    }

    /**
     * 삭제 후 보드 리스트 순서 조정 공통 로직
     */
    private void adjustOrderAfterDelete(List<BoardList> existingLists, Integer deleteOrderNum) {
        existingLists.stream()
                .filter(list -> list.getOrderNum() > deleteOrderNum)
                .forEach(list -> list.updateOrderNum(list.getOrderNum() - 1));
    }
}

