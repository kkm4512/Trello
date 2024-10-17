package com.example.trello.domain.board.validation;

import com.example.trello.common.exception.BoardException;
import com.example.trello.common.response.ApiResponseBoardEnum;
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

    // 멤버가 워크스페이스에 속해있는지 확인
    public void validateMemberInWorkspace(Long memberId, Long workspaceId) {
        if (!memberRepository.existsByUserIdAndWorkspaceId(memberId, workspaceId)) {
            throw new BoardException(ApiResponseBoardEnum.WORKSPACE_ACCESS_DENIED);
        }

    }

}
