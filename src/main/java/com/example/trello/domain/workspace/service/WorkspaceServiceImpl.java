package com.example.trello.domain.workspace.service;

import com.example.trello.common.exception.WorkspaceException;
import com.example.trello.common.response.ApiResponse;
import com.example.trello.common.response.ApiResponseWorkspaceEnum;
import com.example.trello.domain.member.entity.Member;
import com.example.trello.domain.member.enums.MemberRole;
import com.example.trello.domain.member.repository.MemberRepository;
import com.example.trello.domain.user.dto.AuthUser;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.repository.UserRepository;
import com.example.trello.domain.workspace.dto.request.WorkspaceCreateRequest;
import com.example.trello.domain.workspace.dto.request.WorkspaceUpdateRequest;
import com.example.trello.domain.workspace.dto.response.WorkspaceCreateResponse;
import com.example.trello.domain.workspace.dto.response.WorkspaceGetUserResponse;
import com.example.trello.domain.workspace.dto.response.WorkspaceUpdateResponse;
import com.example.trello.domain.workspace.entity.Workspace;
import com.example.trello.domain.workspace.repository.WorkspaceRepository;
import com.example.trello.domain.workspace.validation.WorkspaceValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final WorkspaceValidator workspaceValidator;

    // 워크스페이스 생성
    @Transactional
    @Override
    public ApiResponse<WorkspaceCreateResponse> createWorkspace(AuthUser authUser, WorkspaceCreateRequest request) {
        workspaceValidator.validateAuthUser(authUser);
        workspaceValidator.validateAdminRole(authUser);

        Workspace workspace = createAndSaveWorkspace(request);
        createAndSaveMember(authUser, workspace);

        WorkspaceCreateResponse response = buildWorkspaceCreateResponse(workspace);
        return ApiResponse.of(ApiResponseWorkspaceEnum.WORKSPACE_CREATE_SUCCESS, response);
    }

    // 워크스페이스 생성 로직
    private Workspace createAndSaveWorkspace(WorkspaceCreateRequest request) {
        Workspace workspace = new Workspace();
        workspace.setTitle(request.getTitle());
        workspace.setDescription(request.getDescription());
        workspaceRepository.save(workspace);
        return workspace;
    }

    // 워크스페이스 멤버 생성 로직
    private void createAndSaveMember(AuthUser authUser, Workspace workspace) {
        User user = userRepository.findById(authUser.getId())
                .orElseThrow(() -> new WorkspaceException(ApiResponseWorkspaceEnum.USER_NOT_FOUND));

        Member member = new Member();
        member.setWorkspace(workspace);
        member.setUser(user);
        member.setMemberRole(MemberRole.WORKSPACE_ADMIN);
        memberRepository.save(member);
    }

    // 워크스페이스 생성 응답 로직
    private WorkspaceCreateResponse buildWorkspaceCreateResponse(Workspace workspace) {
        return WorkspaceCreateResponse.builder()
                .id(workspace.getId())
                .title(workspace.getTitle())
                .description(workspace.getDescription())
                .createdAt(workspace.getCreatedAt())
                .updatedAt(workspace.getUpdatedAt())
                .build();
    }

    // 워크스페이스 조회
    @Transactional(readOnly = true)
    @Override
    public ApiResponse<List<WorkspaceGetUserResponse>> getUserWorkspaces(AuthUser authUser) {
        workspaceValidator.validateAuthUser(authUser);

        List<Workspace> workspaces = workspaceRepository.findByMembersUserId(authUser.getId());

        List<WorkspaceGetUserResponse> responses = workspaces.stream()
                .map(workspace -> {
                    Member member = memberRepository.findByUserIdAndWorkspaceId(authUser.getId(), workspace.getId())
                            .orElseThrow(() -> new WorkspaceException(ApiResponseWorkspaceEnum.WORKSPACE_ACCESS_DENIED));

                    return WorkspaceGetUserResponse.builder()
                            .id(workspace.getId())
                            .title(workspace.getTitle())
                            .description(workspace.getDescription())
                            .createdAt(workspace.getCreatedAt())
                            .updatedAt(workspace.getUpdatedAt())
                            .memberRole(member.getMemberRole().name())
                            .build();
                })
                .collect(Collectors.toList());

        return ApiResponse.of(ApiResponseWorkspaceEnum.WORKSPACE_READ_SUCCESS, responses);
    }

    // 워크스페이스 수정
    @Transactional
    @Override
    public ApiResponse<WorkspaceUpdateResponse> updateWorkspace(AuthUser authUser, Long workspaceId, WorkspaceUpdateRequest request) {
        workspaceValidator.validateAuthUser(authUser);
        workspaceValidator.validateWorkspaceAdminRole(authUser, workspaceId);

        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new WorkspaceException(ApiResponseWorkspaceEnum.WORKSPACE_NOT_FOUND));

        workspace.setTitle(request.getTitle());
        workspace.setDescription(request.getDescription());
        workspaceRepository.save(workspace);

        WorkspaceUpdateResponse response = WorkspaceUpdateResponse.builder()
                .id(workspace.getId())
                .title(workspace.getTitle())
                .description(workspace.getDescription())
                .updatedAt(workspace.getUpdatedAt())
                .build();

        return ApiResponse.of(ApiResponseWorkspaceEnum.WORKSPACE_UPDATE_SUCCESS, response);
    }

    // 워크스페이스 삭제
    @Transactional
    @Override
    public ApiResponse<Void> deleteWorkspace(AuthUser authUser, Long workspaceId) {
        workspaceValidator.validateAuthUser(authUser);
        workspaceValidator.validateWorkspaceAdminRole(authUser, workspaceId);

        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new WorkspaceException(ApiResponseWorkspaceEnum.WORKSPACE_NOT_FOUND));

        workspaceRepository.delete(workspace);
        return ApiResponse.of(ApiResponseWorkspaceEnum.WORKSPACE_DELETE_SUCCESS, null);
    }
}
