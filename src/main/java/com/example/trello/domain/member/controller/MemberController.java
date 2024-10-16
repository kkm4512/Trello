package com.example.trello.domain.member.controller;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.domain.member.dto.request.MemberCreateRequest;
import com.example.trello.domain.member.dto.request.MemberUpdateRequest;
import com.example.trello.domain.member.dto.response.MemberListResponse;
import com.example.trello.domain.member.dto.response.MemberResponse;
import com.example.trello.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workspaces/{workspaceId}/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<ApiResponse<MemberResponse>> createMember(
            @PathVariable Long workspaceId,
            @RequestBody MemberCreateRequest request) {
        ApiResponse<MemberResponse> response = memberService.createMember(workspaceId, request);
        return ApiResponse.of(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MemberListResponse>>> getMembers(@PathVariable Long workspaceId) {
        ApiResponse<List<MemberListResponse>> response = memberService.getMembers(workspaceId);
        return ApiResponse.of(response);
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<ApiResponse<MemberResponse>> updateMemberRole(
            @PathVariable Long workspaceId,
            @PathVariable Long memberId,
            @RequestBody MemberUpdateRequest request) {
        ApiResponse<MemberResponse> response = memberService.updateMemberRole(workspaceId, memberId, request);
        return ApiResponse.of(response);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<ApiResponse<Void>> deleteMember(
            @PathVariable Long workspaceId,
            @PathVariable Long memberId) {
        ApiResponse<Void> response = memberService.deleteMember(workspaceId, memberId);
        return ApiResponse.of(response);
    }
}