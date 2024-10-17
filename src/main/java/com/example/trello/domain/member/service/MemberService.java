// MemberService.java (서비스 인터페이스)
package com.example.trello.domain.member.service;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.domain.member.dto.request.MemberCreateRequest;
import com.example.trello.domain.member.dto.request.MemberUpdateRequest;
import com.example.trello.domain.member.dto.response.MemberListResponse;
import com.example.trello.domain.member.dto.response.MemberResponse;

import java.util.List;

public interface MemberService {

    ApiResponse<MemberResponse> createMember(Long workspaceId, MemberCreateRequest request, Long adminId);

    ApiResponse<List<MemberListResponse>> getMembers(Long workspaceId, Long adminId);

    ApiResponse<MemberResponse> updateMemberRole(Long workspaceId, Long memberId, MemberUpdateRequest request, Long adminId);

    ApiResponse<Void> deleteMember(Long workspaceId, Long memberId, Long adminId);

}
