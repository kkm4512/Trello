package com.example.trello.domain.workspace.controller;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.domain.workspace.dto.request.WorkspaceCreateRequest;
import com.example.trello.domain.workspace.dto.request.WorkspaceUpdateRequest;
import com.example.trello.domain.workspace.dto.response.WorkspaceResponse;
import com.example.trello.domain.workspace.service.WorkspaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Workspace", description = "워크스페이스 API")
@RestController
@RequestMapping("/workspaces")
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @Operation(summary = "워크스페이스 생성", description = "워크스페이스를 생성합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<WorkspaceResponse>> createWorkspace(@RequestBody WorkspaceCreateRequest request) {
        ApiResponse<WorkspaceResponse> response = workspaceService.createWorkspace(request);
        return ApiResponse.of(response);
    }

    @Operation(summary = "워크스페이스 목록 조회", description = "워크스페이스 목록을 조회합니다.")
    @GetMapping("/{workspaceId}")
    public ResponseEntity<ApiResponse<WorkspaceResponse>> getWorkspace(@PathVariable Long workspaceId) {
        ApiResponse<WorkspaceResponse> response = workspaceService.getWorkspace(workspaceId);
        return ApiResponse.of(response);
    }

    @Operation(summary = "워크스페이스 수정", description = "워크스페이스를 수정합니다.")
    @PutMapping("/{workspaceId}")
    public ResponseEntity<ApiResponse<WorkspaceResponse>> updateWorkspace(@PathVariable Long workspaceId, @RequestBody WorkspaceUpdateRequest request) {
        ApiResponse<WorkspaceResponse> response = workspaceService.updateWorkspace(workspaceId, request);
        return ApiResponse.of(response);
    }

    @Operation(summary = "워크스페이스 삭제", description = "워크스페이스를 삭제합니다.")
    @DeleteMapping("/{workspaceId}")
    public ResponseEntity<ApiResponse<Void>> deleteWorkspace(@PathVariable Long workspaceId) {
        ApiResponse<Void> response = workspaceService.deleteWorkspace(workspaceId);
        return ApiResponse.of(response);
    }
}