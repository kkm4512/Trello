package com.example.trello.domain.list.service;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.common.response.ApiResponseEnum;
import com.example.trello.common.response.ApiResponseTestEnum;
import com.example.trello.domain.board.entity.Board;
import com.example.trello.domain.board.repository.BoardRepository;
import com.example.trello.domain.list.dto.request.ListRequestDto;
import com.example.trello.domain.list.dto.response.ListResponseDto;
import com.example.trello.domain.list.entity.BoardList;
import com.example.trello.domain.list.repository.ListRepository;
import com.example.trello.domain.user.dto.AuthUser;
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
    public ApiResponse<ListResponseDto> createList(Long userId, Long workspaceId, Long boardId, ListRequestDto requestDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException());

        // 읽기 전용 멤버일 시 에러 추가

        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new NoSuchElementException());

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException());

        BoardList boardList = BoardList.builder()
                .board(board)
                .user(user)
                .title(requestDto.getTitle())
                .orderNum(1) // 기존 리스트들 맨 앞에 새로운 리스트 추가
                .build();

        // 기존 리스트들 순서 전부 + 1
        List<BoardList> existingList = listRepository.findByBoardId(boardId);

        existingList.stream()
                .forEach(list -> list.updateOrderNum(list.getOrderNum() + 1));

        listRepository.save(boardList); // 새로 생성한 리스트 저장

        ListResponseDto responseData = ListResponseDto.of(boardList); // DTO로 변환

        ApiResponseEnum apiResponseEnum = ApiResponseTestEnum.TEST_SUCCESS;
        ApiResponse<ListResponseDto> apiResponse = new ApiResponse<>(apiResponseEnum, responseData);
        return apiResponse;
    }

    /**
     * 보드 리스트 제목 or 순서 수정
     */
    public ApiResponse<ListResponseDto> updateList(Long userId, Long workspaceId, Long boardId, Long listId, ListRequestDto requestDto) {

        // 유저 가져오기
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException());

        // 읽기 전용 멤버일 시 에러 추가

        // 워크 스페이스 있는지 확인
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new NoSuchElementException());

        // 보드 있는지 확인
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException());

        // 보드 리스트 가져오기
        BoardList boardList = listRepository.findById(listId).orElseThrow();

        Integer existingOrderNum = boardList.getOrderNum(); // 리스트의 기존 순서 저장


        boardList.updateTitle(requestDto.getTitle()); // 리스트 제목 업데이트
        boardList.updateOrderNum(requestDto.getOrderNum()); // 리스트 순서 업데이트

        // 만약 리스트 순서 변경했으면 보드 내의 다른 리스트들도 그에 따른 순서 업데이트
        if (existingOrderNum != boardList.getOrderNum()) {
            List<BoardList> existingList = listRepository.findByBoardId(boardId); // 보드 안에 있는 모든 리스트 가져오기
            existingList.stream()
                    .filter(list -> list.getOrderNum() >= requestDto.getOrderNum()) // 옮긴 리스트의 현 위치보다 뒤에 있는 리스트
                    .forEach(targetList -> targetList.updateOrderNum(targetList.getOrderNum() + 1)); // 순서 하나씩 뒤로 밀기
        }

        ListResponseDto responseData = ListResponseDto.of(boardList); // DTO로 변환

        ApiResponseEnum apiResponseEnum = ApiResponseTestEnum.TEST_SUCCESS;
        ApiResponse<ListResponseDto> apiResponse = new ApiResponse<>(apiResponseEnum, responseData);
        return apiResponse;
    }
}
