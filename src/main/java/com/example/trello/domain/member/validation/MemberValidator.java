package com.example.trello.domain.member.validation;

import com.example.trello.common.exception.MemberException;
import com.example.trello.common.response.ApiResponseMemberEnum;
import com.example.trello.domain.member.dto.request.MemberUpdateRequest;
import com.example.trello.domain.member.entity.Member;
import com.example.trello.domain.member.enums.MemberRole;
import com.example.trello.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberValidator {

    private final MemberRepository memberRepository;

    // 워크스페이스 관리자 또는 멤버 역할 확인
    public void validateAdminOrMemberRole(Long memberId, Long workspaceId) {
        Member member = memberRepository.findByUserIdAndWorkspaceId(memberId, workspaceId)
                .orElseThrow(() -> new MemberException(ApiResponseMemberEnum.WORKSPACE_ACCESS_DENIED));

        if (member.getMemberRole() != MemberRole.WORKSPACE_ADMIN && member.getMemberRole() != MemberRole.BOARD_MEMBER) {
            throw new MemberException(ApiResponseMemberEnum.WORKSPACE_ACCESS_DENIED);
        }
    }

    // 워크스페이스 관리자 역할 확인
    public void validateWorkspaceAdminRole(Long memberId, Long workspaceId) {
        Member member = memberRepository.findByUserIdAndWorkspaceId(memberId, workspaceId)
                .orElseThrow(() -> new MemberException(ApiResponseMemberEnum.WORKSPACE_ACCESS_DENIED));

        if (member.getMemberRole() != MemberRole.WORKSPACE_ADMIN) {
            throw new MemberException(ApiResponseMemberEnum.WORKSPACE_ACCESS_DENIED);
        }
    }


    // 워크스페이스에 속한 멤버인지 확인 (READ_ONLY, BOARD_MEMBER 포함)
    public void validateMemberInWorkspaceRead(Long userId, Long workspaceId) {
        Member member = memberRepository.findByUserIdAndWorkspaceId(userId, workspaceId)
                .orElseThrow(() -> new MemberException(ApiResponseMemberEnum.WORKSPACE_ACCESS_DENIED));

        // 모든 멤버(READ_ONLY, BOARD_MEMBER, WORKSPACE_ADMIN)가 조회 가능
        if (member.getMemberRole() == MemberRole.READ_ONLY ||
                member.getMemberRole() == MemberRole.BOARD_MEMBER ||
                member.getMemberRole() == MemberRole.WORKSPACE_ADMIN) {
            return; // 조회 권한이 있음
        }

        throw new MemberException(ApiResponseMemberEnum.WORKSPACE_ACCESS_DENIED);
    }

    // 멤버 역할 수정 유효성 검사
    public void validateUpdateRequest(Long workspaceId, Long memberId, MemberUpdateRequest request, Long currentUserId) {
        if (workspaceId <= 0) {
            throw new MemberException(ApiResponseMemberEnum.WORKSPACE_NOT_FOUND);
        }

        if (memberId <= 0) {
            throw new MemberException(ApiResponseMemberEnum.MEMBER_NOT_FOUND);
        }

        // 자신의 역할 변경을 막는 로직
        if (currentUserId.equals(memberId)) {
            throw new MemberException(ApiResponseMemberEnum.CANNOT_CHANGE_OWN_ROLE);
        }

        // 멤버 역할 유효성 검사
        try {
            MemberRole.valueOf(request.getMemberRole());
        } catch (IllegalArgumentException e) {
            throw new MemberException(ApiResponseMemberEnum.MEMBER_ROLE_INVALID);
        }

        if (!memberRepository.existsByIdAndWorkspaceId(memberId, workspaceId)) {
            throw new MemberException(ApiResponseMemberEnum.MEMBER_NOT_FOUND);
        }
    }

    // 멤버 삭제 요청 유효성 검사
    public void validateDeleteRequest(Long workspaceId, Long memberId, Long currentUserId) {
        if (workspaceId <= 0) {
            throw new MemberException(ApiResponseMemberEnum.WORKSPACE_NOT_FOUND);
        }

        if (memberId <= 0) {
            throw new MemberException(ApiResponseMemberEnum.MEMBER_NOT_FOUND);
        }

        if (currentUserId.equals(memberId)) {
            throw new MemberException(ApiResponseMemberEnum.CANNOT_DELETE_OWN_MEMBER);
        }

        if (!memberRepository.existsByIdAndWorkspaceId(memberId, workspaceId)) {
            throw new MemberException(ApiResponseMemberEnum.MEMBER_NOT_FOUND);
        }
    }

}






