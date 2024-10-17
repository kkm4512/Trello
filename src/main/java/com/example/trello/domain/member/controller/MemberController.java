package com.example.trello.domain.member.controller;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.domain.member.dto.request.MemberCreateRequest;
import com.example.trello.domain.member.dto.request.MemberUpdateRequest;
import com.example.trello.domain.member.dto.response.MemberListResponse;
import com.example.trello.domain.member.dto.response.MemberResponse;
import com.example.trello.domain.member.service.MemberService;
import com.example.trello.domain.user.dto.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Member", description = "이정현")
@RestController
@RequestMapping("/workspaces/{workspaceId}/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "멤버 추가", description = "워크스페이스에 멤버를 추가합니다. WORK_SPACE_ADMIN 역할을 가진 계정만 멤버 추가가 가능합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<MemberResponse>> createMember(
            @PathVariable Long workspaceId,
            @RequestBody MemberCreateRequest request,
            @AuthenticationPrincipal AuthUser authUser
    ) {
        ApiResponse<MemberResponse> apiResponse = memberService.createMember(workspaceId, request, authUser.getId());
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @Operation(summary = "멤버 목록 조회", description = "워크스페이스에 속한 멤버 목록을 조회합니다. 본인이 속한 워크스페이스만 조회 가능합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<MemberListResponse>>> getMembers(
            @PathVariable Long workspaceId,
            @AuthenticationPrincipal AuthUser authUser
    ) {
        ApiResponse<List<MemberListResponse>> apiResponse = memberService.getMembers(workspaceId, authUser.getId());
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @Operation(summary = "멤버 권한 수정", description = "워크스페이스에 속한 멤버의 권한을 수정합니다. WORK_SPACE_ADMIN 역할을 가진 계정만 멤버 권한 수정이 가능합니다.")
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

    @Operation(summary = "멤버 삭제", description = "워크스페이스에 속한 멤버를 삭제합니다. 본인을 삭제할 수 없으며, WORK_SPACE_ADMIN 역할을 가진 계정만 멤버 삭제가 가능합니다.")
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
