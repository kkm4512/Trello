package com.example.trello.domain.member.service;

import com.example.trello.common.exception.MemberException;
import com.example.trello.common.response.ApiResponse;
import com.example.trello.common.response.ApiResponseMemberEnum;
import com.example.trello.domain.member.dto.request.MemberCreateRequest;
import com.example.trello.domain.member.dto.request.MemberUpdateRequest;
import com.example.trello.domain.member.dto.response.MemberListResponse;
import com.example.trello.domain.member.dto.response.MemberResponse;
import com.example.trello.domain.member.entity.Member;
import com.example.trello.domain.member.enums.MemberRole;
import com.example.trello.domain.member.repository.MemberRepository;
import com.example.trello.domain.member.validation.MemberValidator;
import com.example.trello.domain.workspace.entity.Workspace;
import com.example.trello.domain.workspace.repository.WorkspaceRepository;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final WorkspaceRepository workspaceRepository;
    private final UserRepository userRepository;
    private final MemberValidator memberValidator;

    @Transactional
    @Override
    public ApiResponse<MemberResponse> createMember(Long workspaceId, MemberCreateRequest request, Long adminId) {
        // 관리자가 맞는지 확인 (WORKSPACE_ADMIN)
        memberValidator.validateWorkspaceAdmin(adminId, workspaceId);

        // 멤버 생성 요청 검증
        memberValidator.validateCreateRequest(workspaceId, request);

        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new MemberException(ApiResponseMemberEnum.WORKSPACE_NOT_FOUND));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new MemberException(ApiResponseMemberEnum.USER_NOT_FOUND));

        if (memberRepository.existsByUserIdAndWorkspaceId(user.getId(), workspace.getId())) {
            throw new MemberException(ApiResponseMemberEnum.MEMBER_ALREADY_EXISTS);
        }

        // 새로운 멤버 생성
        Member member = new Member();
        member.setWorkspace(workspace);
        member.setUser(user);
        member.setMemberRole(MemberRole.READ_ONLY);
        memberRepository.save(member);

        MemberResponse response = buildMemberResponse(member);
        return ApiResponse.of(ApiResponseMemberEnum.MEMBER_CREATE_SUCCESS, response);
    }

    @Transactional(readOnly = true)
    @Override
    public ApiResponse<List<MemberListResponse>> getMembers(Long workspaceId, Long userId) {
        // 멤버가 해당 워크스페이스에 속하는지 확인
        memberValidator.validateMemberInWorkspace(userId, workspaceId);

        // 해당 워크스페이스의 멤버 조회
        List<Member> members = memberRepository.findAllByWorkspaceId(workspaceId);

        List<MemberListResponse> responses = members.stream()
                .map(this::buildMemberListResponse)
                .collect(Collectors.toList());

        return ApiResponse.of(ApiResponseMemberEnum.MEMBER_READ_SUCCESS, responses);
    }

    @Transactional
    @Override
    public ApiResponse<MemberResponse> updateMemberRole(Long workspaceId, Long memberId, MemberUpdateRequest request, Long adminId) {
        // 관리자가 맞는지 확인 (WORKSPACE_ADMIN)
        memberValidator.validateWorkspaceAdmin(adminId, workspaceId);

        // 자기 자신의 역할 변경을 막는 검증 추가
        memberValidator.validateUpdateRequest(workspaceId, memberId, request, adminId);

        Member member = memberRepository.findByIdAndWorkspaceId(memberId, workspaceId)
                .orElseThrow(() -> new MemberException(ApiResponseMemberEnum.MEMBER_NOT_FOUND));

        // 역할 변경
        member.setMemberRole(MemberRole.valueOf(request.getMemberRole()));
        memberRepository.save(member);

        MemberResponse response = buildMemberResponse(member);
        return ApiResponse.of(ApiResponseMemberEnum.MEMBER_UPDATE_SUCCESS, response);
    }

    @Transactional
    @Override
    public ApiResponse<Void> deleteMember(Long workspaceId, Long memberId, Long adminId) {
        // 관리자가 맞는지 확인 (WORKSPACE_ADMIN)
        memberValidator.validateWorkspaceAdmin(adminId, workspaceId);

        memberValidator.validateDeleteRequest(workspaceId, memberId, adminId);

        Member member = memberRepository.findByIdAndWorkspaceId(memberId, workspaceId)
                .orElseThrow(() -> new MemberException(ApiResponseMemberEnum.MEMBER_NOT_FOUND));

        memberRepository.delete(member);
        return ApiResponse.of(ApiResponseMemberEnum.MEMBER_DELETE_SUCCESS, null);
    }

    private MemberResponse buildMemberResponse(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .workspaceId(member.getWorkspace().getId())
                .userId(member.getUser().getId())
                .memberRole(member.getMemberRole().name())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt())
                .build();
    }

    private MemberListResponse buildMemberListResponse(Member member) {
        return MemberListResponse.builder()
                .id(member.getId())
                .workspaceId(member.getWorkspace().getId())
                .userId(member.getUser().getId())
                .email(member.getUser().getEmail())
                .memberRole(member.getMemberRole().name())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt())
                .build();
    }

}


