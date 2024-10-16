package com.example.trello.domain.member.controller;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.domain.member.dto.request.MemberCreateRequest;
import com.example.trello.domain.member.dto.request.MemberUpdateRequest;
import com.example.trello.domain.member.dto.response.MemberListResponse;
import com.example.trello.domain.member.dto.response.MemberResponse;
import com.example.trello.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Member", description = "멤버 API")
@RestController
@RequestMapping("/workspaces/{workspaceId}/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "멤버 생성", description = "워크스페이스에 멤버를 생성합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<MemberResponse>> createMember(
            @PathVariable Long workspaceId,
            @RequestBody MemberCreateRequest request) {
        ApiResponse<MemberResponse> response = memberService.createMember(workspaceId, request);
        return ApiResponse.of(response);
    }

    @Operation(summary = "멤버 조회", description = "워크스페이스의 멤버 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<MemberListResponse>>> getMembers(@PathVariable Long workspaceId) {
        ApiResponse<List<MemberListResponse>> response = memberService.getMembers(workspaceId);
        return ApiResponse.of(response);
    }

    @Operation(summary = "멤버 수정", description = "워크스페이스의 멤버 역할을 수정합니다.")
    @PutMapping("/{memberId}")
    public ResponseEntity<ApiResponse<MemberResponse>> updateMemberRole(
            @PathVariable Long workspaceId,
            @PathVariable Long memberId,
            @RequestBody MemberUpdateRequest request) {
        ApiResponse<MemberResponse> response = memberService.updateMemberRole(workspaceId, memberId, request);
        return ApiResponse.of(response);
    }

    @Operation(summary = "멤버 삭제", description = "워크스페이스의 멤버를 삭제합니다.")
    @DeleteMapping("/{memberId}")
    public ResponseEntity<ApiResponse<Void>> deleteMember(
            @PathVariable Long workspaceId,
            @PathVariable Long memberId) {
        ApiResponse<Void> response = memberService.deleteMember(workspaceId, memberId);
        return ApiResponse.of(response);
    }
}