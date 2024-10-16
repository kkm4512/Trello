package com.example.trello.domain.workspace.controller;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.domain.user.dto.AuthUser;
import com.example.trello.domain.workspace.dto.request.WorkspaceCreateRequest;
import com.example.trello.domain.workspace.dto.request.WorkspaceUpdateRequest;
import com.example.trello.domain.workspace.dto.response.WorkspaceCreateResponse;
import com.example.trello.domain.workspace.dto.response.WorkspaceGetUserResponse;
import com.example.trello.domain.workspace.dto.response.WorkspaceUpdateResponse;
import com.example.trello.domain.workspace.service.WorkspaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Workspace", description = "워크스페이스 API")
@RestController
@RequestMapping("/workspaces")
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @Operation(summary = "워크스페이스 생성", description = "워크스페이스를 생성합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<WorkspaceCreateResponse>> createWorkspace(@AuthenticationPrincipal AuthUser authUser, @RequestBody WorkspaceCreateRequest request) {
        ApiResponse<WorkspaceCreateResponse> apiResponse = workspaceService.createWorkspace(authUser, request);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);

    }

    @Operation(summary = "사용자의 워크스페이스 목록 조회", description = "사용자가 속한 워크스페이스 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<WorkspaceGetUserResponse>>> getUserWorkspaces(@AuthenticationPrincipal AuthUser authUser) {
        ApiResponse<List<WorkspaceGetUserResponse>> apiResponse = workspaceService.getUserWorkspaces(authUser);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @Operation(summary = "워크스페이스 수정", description = "워크스페이스를 수정합니다.")
    @PutMapping("/{workspaceId}")
    public ResponseEntity<ApiResponse<WorkspaceUpdateResponse>> updateWorkspace(@AuthenticationPrincipal AuthUser authUser, @PathVariable Long workspaceId, @RequestBody WorkspaceUpdateRequest request) {
        ApiResponse<WorkspaceUpdateResponse> apiResponse = workspaceService.updateWorkspace(authUser, workspaceId, request);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @Operation(summary = "워크스페이스 삭제", description = "워크스페이스를 삭제합니다.")
    @DeleteMapping("/{workspaceId}")
    public ResponseEntity<ApiResponse<Void>> deleteWorkspace(@AuthenticationPrincipal AuthUser authUser, @PathVariable Long workspaceId) {
        ApiResponse<Void> apiResponse = workspaceService.deleteWorkspace(authUser, workspaceId);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

}
