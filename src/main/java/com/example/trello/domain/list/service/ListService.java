package com.example.trello.domain.list.service;

import com.example.trello.common.response.ApiRasponseWorkspaceEnum;
import com.example.trello.common.response.ApiResponse;
import com.example.trello.common.response.ApiResponseListEnum;
import com.example.trello.domain.board.entity.Board;
import com.example.trello.domain.board.repository.BoardRepository;
import com.example.trello.domain.list.dto.request.ListCreateRequestDto;
import com.example.trello.domain.list.dto.request.ListUpdateDto;
import com.example.trello.domain.list.dto.response.ListResponseDto;
import com.example.trello.domain.list.entity.BoardList;
import com.example.trello.domain.list.repository.ListRepository;
import com.example.trello.domain.member.entity.Member;
import com.example.trello.domain.member.enums.MemberRole;
import com.example.trello.domain.member.repository.MemberRepository;
import com.example.trello.domain.user.repository.UserRepository;
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
    private final MemberRepository memberRepository;

    /**
     * 보드 리스트 생성
     */
    public ApiResponse<ListResponseDto> createList(Long userId, Long workspaceId, Long boardId, ListCreateRequestDto requestDto) {
        // 검증 및 보드 가져오기
        Board board = validateUserWorkspaceBoard(userId, workspaceId, boardId);
        validateMemberRole(userId);

        // 새로운 리스트 생성
        BoardList boardList = BoardList.builder()
                .board(board)
                .user(userRepository.findById(userId).get()) // 이미 검증된 유저
                .title(requestDto.getTitle())
                .orderNum(1) // 새 리스트는 맨 앞에 추가
                .build();

        // 기존 리스트들 순서 전부 + 1
        listRepository.findAllByBoardId(boardId).forEach(list -> list.updateOrderNum(list.getOrderNum() + 1));

        listRepository.save(boardList);

        ListResponseDto responseData = ListResponseDto.of(boardList);
        return new ApiResponse<>(ApiResponseListEnum.LIST_CREATE_OK, responseData);
    }

    /**
     * 보드 리스트 제목 or 순서 수정
     */
    public ApiResponse<ListResponseDto> updateList(Long userId, Long workspaceId, Long boardId, Long listId, ListUpdateDto requestDto) {
        // 검증 및 보드 가져오기
        Board board = validateUserWorkspaceBoard(userId, workspaceId, boardId);

        // 멤버 역할 검증
        validateMemberRole(userId);

        // 수정할 리스트 가져오기
        BoardList boardList = listRepository.findById(listId)
                .orElseThrow(() -> new NoSuchElementException("리스트를 찾을 수 없습니다."));

        // 기존 순서와 새로운 순서 비교
        Integer existingOrderNum = boardList.getOrderNum();
        Integer newOrderNum = requestDto.getOrderNum() != null ? requestDto.getOrderNum() : existingOrderNum;

        // 리스트 옮기기는 자유로운 드래그 & 드랍 방식(리스트 옮기면 그에 영향 받아서 순서 바뀌는 다른 리스트들 순서 다 최신화)
        if (!existingOrderNum.equals(newOrderNum)) { // 만약 리스트를 옮겨서 순서가 변한다면
            List<BoardList> existingLists = listRepository.findAllByBoardId(boardId); // 같은 보드 내 다른 리스트들 가져오기
            if (existingOrderNum < newOrderNum) { // 리스트를 뒤로 옮겼다면
                existingLists.stream()
                        // 기존 순서보다는 크고 새로운 순서보다는 작은 리스트들 한 칸씩 앞으로 땡기기
                        .filter(list -> list.getOrderNum() > existingOrderNum && list.getOrderNum() <= newOrderNum)
                        .forEach(list -> list.updateOrderNum(list.getOrderNum() - 1));
            } else {
                // 리스트를 앞으로 옮길 때
                existingLists.stream()
                        // 기존 순서보다는 작고 새로운 순서보다는 큰 리스트들 한 칸씩 뒤로 밀기
                        .filter(list -> list.getOrderNum() < existingOrderNum && list.getOrderNum() >= newOrderNum) // 기존 미만 신규 이상 자리의 리스트
                        .forEach(list -> list.updateOrderNum(list.getOrderNum() + 1));
            }
            boardList.updateOrderNum(newOrderNum); // 각 리스트 위치 조정 후 더티 체킹으로 순서 갱신
        }
        boardList.updateTitle(requestDto.getTitle()); // 제목 갱신

        ListResponseDto responseData = ListResponseDto.of(boardList);
        return new ApiResponse<>(ApiResponseListEnum.LIST_UPDATE_OK, responseData);
    }

    /**
     * 보드 리스트 삭제
     */
    public ApiResponse<Void> deleteList(Long userId, Long workspaceId, Long boardId, Long listId) {
        // 검증 및 보드 가져오기
        Board board = validateUserWorkspaceBoard(userId, workspaceId, boardId);

        // 멤버 역할 검증
        validateMemberRole(userId);

        // 삭제할 리스트 가져오기
        BoardList boardList = listRepository.findById(listId)
                .orElseThrow(() -> new NoSuchElementException("리스트를 찾을 수 없습니다."));

        // 삭제할 리스트 순서 조정
        Integer deleteOrderNum = boardList.getOrderNum();
        List<BoardList> lists = listRepository.findAllByBoardId(boardId);

        // 리스트 삭제
        listRepository.delete(boardList);

        // 지워진 리스트 뒤에 있는 리스트들 순서 당기기
        adjustOrderAfterDelete(lists, deleteOrderNum);

        return new ApiResponse<>(ApiResponseListEnum.LIST_DELETED_OK, null);
    }

    // 멤버 맞는지 여부와 멤버의 권한 확인
    private void validateMemberRole(Long userId) {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저는 멤버가 아닙니다."));

        if (member.getMemberRole() == (MemberRole.READ_ONLY)) {
            throw new IllegalArgumentException("읽기 전용(READ_ONLY) 멤버는 리스트를 생성, 수정, 삭제할 수 없습니다.");
        }
    }

     // 유저, 워크스페이스, 보드 검증 공통 로직
    private Board validateUserWorkspaceBoard(Long userId, Long workspaceId, Long boardId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new RuntimeException(ApiRasponseWorkspaceEnum.WORKSPACE_NOT_FOUND.getMessage()));

        return boardRepository.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException("보드를 찾을 수 없습니다."));
    }

     // 삭제 후 보드 리스트 순서 조정 공통 로직
    private void adjustOrderAfterDelete(List<BoardList> existingLists, Integer deleteOrderNum) {
        existingLists.stream()
                .filter(list -> list.getOrderNum() > deleteOrderNum)
                .forEach(list -> {
                    list.updateOrderNum(list.getOrderNum() - 1);
                    listRepository.save(list);
                    log.info("Updated list ID: {}, new orderNum: {}", list.getId(), list.getOrderNum());
                });

    }
}

