package com.example.trello.domain.board.Validation;

import com.example.trello.common.exception.BoardException;
import com.example.trello.common.response.ApiResponseBoardEnum;
import com.example.trello.domain.board.dto.request.BoardRequest;
import com.example.trello.domain.member.entity.Member;
import com.example.trello.domain.member.enums.MemberRole;
import com.example.trello.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardValidator {

    private final MemberRepository memberRepository;

    // 관리자 또는 멤버 권한 확인
    public void validateAdminOrMemberRole(Long memberId, Long workspaceId) {
        Member member = memberRepository.findByUserIdAndWorkspaceId(memberId, workspaceId)
                .orElseThrow(() -> new BoardException(ApiResponseBoardEnum.WORKSPACE_ACCESS_DENIED));

        if (member.getMemberRole() != MemberRole.WORKSPACE_ADMIN && member.getMemberRole() != MemberRole.BOARD_MEMBER) {
            throw new BoardException(ApiResponseBoardEnum.WORKSPACE_ACCESS_DENIED);
        }
    }

    // 관리자 권한 확인
    public void validateAdminRole(Long memberId, Long workspaceId) {
        Member member = memberRepository.findByUserIdAndWorkspaceId(memberId, workspaceId)
                .orElseThrow(() -> new BoardException(ApiResponseBoardEnum.WORKSPACE_ACCESS_DENIED));

        if (member.getMemberRole() != MemberRole.WORKSPACE_ADMIN) {
            throw new BoardException(ApiResponseBoardEnum.WORKSPACE_ADMIN_REQUIRED);
        }
    }

    // 멤버가 워크스페이스에 속해있는지 확인
    public void validateMemberInWorkspace(Long memberId, Long workspaceId) {
        if (!memberRepository.existsByUserIdAndWorkspaceId(memberId, workspaceId)) {
            throw new BoardException(ApiResponseBoardEnum.WORKSPACE_ACCESS_DENIED);
        }
    }

    // 보드 생성 요청 유효성 검사
    public void validateCreateRequest(BoardRequest request) {
        if (request.getTitle() == null || request.getTitle().isEmpty()) {
            throw new BoardException(ApiResponseBoardEnum.BOARD_CREATE_FAIL);
        }
    }

    // 보드 수정 요청 유효성 검사
    public void validateUpdateRequest(BoardRequest request) {
        if (request.getTitle() == null || request.getTitle().isEmpty()) {
            throw new BoardException(ApiResponseBoardEnum.BOARD_UPDATE_FAIL);
        }
    }

}
