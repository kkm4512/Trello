package com.example.trello.domain.workspace.validation;

import com.example.trello.common.exception.WorkspaceException;
import com.example.trello.common.response.ApiResponseWorkspaceEnum;
import com.example.trello.domain.member.entity.Member;
import com.example.trello.domain.member.enums.MemberRole;
import com.example.trello.domain.member.repository.MemberRepository;
import com.example.trello.domain.user.dto.AuthUser;
import com.example.trello.domain.user.entity.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WorkspaceValidator {

    private final MemberRepository memberRepository;

    private static final SimpleGrantedAuthority ADMIN_AUTHORITY = new SimpleGrantedAuthority(UserRole.Authority.ADMIN);

    // 유저 정보가 없거나 유저 아이디가 없는 경우 예외 처리
    public void validateAuthUser(AuthUser authUser) {
        if (authUser == null || authUser.getId() == null) {
            throw new WorkspaceException(ApiResponseWorkspaceEnum.USER_NOT_FOUND);
        }
    }

    // 유저가 관리자 권한이 없는 경우 예외 처리
    public void validateAdminRole(AuthUser authUser) {
        if (authUser.getAuthorities() == null || authUser.getAuthorities().isEmpty() ||
                !authUser.getAuthorities().contains(ADMIN_AUTHORITY)) {
            throw new WorkspaceException(ApiResponseWorkspaceEnum.WORKSPACE_CREATE_ACCESS_DENIED);
        }
    }

    // 워크스페이스 관리자 권한이 없는 경우 예외 처리
    public void validateWorkspaceAdminRole(AuthUser authUser, Long workspaceId) {
        Member member = memberRepository.findByUserIdAndWorkspaceId(authUser.getId(), workspaceId)
                .orElseThrow(() ->  new WorkspaceException(ApiResponseWorkspaceEnum.WORKSPACE_ACCESS_DENIED));

        if (member.getMemberRole() != MemberRole.WORKSPACE_ADMIN) {
            throw new WorkspaceException(ApiResponseWorkspaceEnum.WORKSPACE_ACCESS_DENIED);
        }
    }
}
