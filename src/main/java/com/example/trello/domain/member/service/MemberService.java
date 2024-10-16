package com.example.trello.domain.member.service;

import com.example.trello.common.annotation.MemberAddSlack;
import com.example.trello.common.response.ApiResponse;
import com.example.trello.common.response.ApiResponseMemberEnum;
import com.example.trello.domain.member.dto.request.MemberCreateRequest;
import com.example.trello.domain.member.dto.request.MemberUpdateRequest;
import com.example.trello.domain.member.dto.response.MemberListResponse;
import com.example.trello.domain.member.dto.response.MemberResponse;
import com.example.trello.domain.member.entity.Member;
import com.example.trello.domain.member.enums.MemberRole;
import com.example.trello.domain.member.repository.MemberRepository;
import com.example.trello.domain.workspace.entity.Workspace;
import com.example.trello.domain.workspace.repository.WorkspaceRepository;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final WorkspaceRepository workspaceRepository;
    private final UserRepository userRepository;

    // 멤버 추가
    @MemberAddSlack
    public ApiResponse<MemberResponse> createMember(Long workspaceId, MemberCreateRequest request) {
        try {
            Workspace workspace = workspaceRepository.findById(workspaceId)
                    .orElseThrow(() -> new IllegalArgumentException("Workspace not found"));
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            Member member = new Member();
            member.setWorkspace(workspace);
            member.setUser(user);
            member.setEmail(user.getEmail());
            member.setMemberRole(MemberRole.READ_ONLY);
            memberRepository.save(member);

            MemberResponse response = new MemberResponse(
                    member.getId(),
                    workspaceId,
                    user.getId(),
                    user.getEmail(),
                    member.getMemberRole().name(),
                    member.getCreatedAt(),
                    member.getUpdatedAt());

            return ApiResponse.of(ApiResponseMemberEnum.MEMBER_CREATE_SUCCESS, response);
        } catch (Exception e) {
            return ApiResponse.of(ApiResponseMemberEnum.MEMBER_CREATE_FAIL, null);
        }
    }

    // 멤버 목록 조회
    public ApiResponse<List<MemberListResponse>> getMembers(Long workspaceId) {
        try {
            List<Member> members = memberRepository.findAllByWorkspaceId(workspaceId);
            List<MemberListResponse> response = members.stream()
                    .map(member -> new MemberListResponse(
                            member.getId(),
                            member.getWorkspace().getId(),
                            member.getUser().getId(),
                            member.getEmail(),
                            member.getMemberRole().name(),
                            member.getCreatedAt(),
                            member.getUpdatedAt()))
                    .collect(Collectors.toList());

            return ApiResponse.of(ApiResponseMemberEnum.MEMBER_READ_SUCCESS, response);
        } catch (Exception e) {
            return ApiResponse.of(ApiResponseMemberEnum.MEMBER_READ_FAIL, null);
        }
    }

    // 멤버 역할 변경
    public ApiResponse<MemberResponse> updateMemberRole(Long workspaceId, Long memberId, MemberUpdateRequest request) {
        try {
            Member member = memberRepository.findByIdAndWorkspaceId(memberId, workspaceId)
                    .orElseThrow(() -> new IllegalArgumentException("Member not found"));

            member.setMemberRole(MemberRole.valueOf(request.getMemberRole()));
            memberRepository.save(member);

            MemberResponse response = new MemberResponse(
                    member.getId(),
                    member.getWorkspace().getId(),
                    member.getUser().getId(),
                    member.getEmail(),
                    member.getMemberRole().name(),
                    member.getCreatedAt(),
                    member.getUpdatedAt());

            return ApiResponse.of(ApiResponseMemberEnum.MEMBER_UPDATE_SUCCESS, response);
        } catch (Exception e) {
            return ApiResponse.of(ApiResponseMemberEnum.MEMBER_UPDATE_FAIL, null);
        }
    }

    // 멤버 삭제
    public ApiResponse<Void> deleteMember(Long workspaceId, Long memberId) {
        try {
            Member member = memberRepository.findByIdAndWorkspaceId(memberId, workspaceId)
                    .orElseThrow(() -> new IllegalArgumentException(ApiResponseMemberEnum.MEMBER_NOT_FOUND.getMessage()));
            memberRepository.delete(member);
            return ApiResponse.of(ApiResponseMemberEnum.MEMBER_DELETE_SUCCESS, null);
        } catch (IllegalArgumentException e) {
            return ApiResponse.of(ApiResponseMemberEnum.MEMBER_NOT_FOUND, null);
        } catch (Exception e) {
            return ApiResponse.of(ApiResponseMemberEnum.MEMBER_DELETE_FAIL, null);
        }
    }

}