package com.example.trello.domain.member.controller;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.domain.member.dto.request.MemberCreateRequest;
import com.example.trello.domain.member.dto.request.MemberUpdateRequest;
import com.example.trello.domain.member.dto.response.MemberListResponse;
import com.example.trello.domain.member.dto.response.MemberResponse;
import com.example.trello.domain.member.service.MemberService;
import com.example.trello.domain.user.dto.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            @RequestBody MemberCreateRequest request,
            @AuthenticationPrincipal AuthUser authUser
    ) {
        ApiResponse<MemberResponse> apiResponse = memberService.createMember(workspaceId, request, authUser.getId());
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MemberListResponse>>> getMembers(
            @PathVariable Long workspaceId,
            @AuthenticationPrincipal AuthUser authUser
    ) {
        ApiResponse<List<MemberListResponse>> apiResponse = memberService.getMembers(workspaceId, authUser.getId());
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<ApiResponse<MemberResponse>> updateMemberRole(
            @PathVariable Long workspaceId,
            @PathVariable Long memberId,
            @RequestBody MemberUpdateRequest request,
            @AuthenticationPrincipal AuthUser authUser
    ) {
        ApiResponse<MemberResponse> apiResponse = memberService.updateMemberRole(workspaceId, memberId, request, authUser.getId());
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<ApiResponse<Void>> deleteMember(
            @PathVariable Long workspaceId,
            @PathVariable Long memberId,
            @AuthenticationPrincipal AuthUser authUser
    ) {
        ApiResponse<Void> apiResponse = memberService.deleteMember(workspaceId, memberId, authUser.getId());
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
