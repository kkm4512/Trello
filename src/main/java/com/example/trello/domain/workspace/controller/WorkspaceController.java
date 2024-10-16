package com.example.trello.domain.workspace.controller;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.domain.workspace.dto.request.WorkspaceCreateRequest;
import com.example.trello.domain.workspace.dto.request.WorkspaceUpdateRequest;
import com.example.trello.domain.workspace.dto.response.WorkspaceResponse;
import com.example.trello.domain.workspace.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workspaces")
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @PostMapping
    public ResponseEntity<ApiResponse<WorkspaceResponse>> createWorkspace(@RequestBody WorkspaceCreateRequest request) {
        ApiResponse<WorkspaceResponse> response = workspaceService.createWorkspace(request);
        return ApiResponse.of(response);
    }

    @GetMapping("/{workspaceId}")
    public ResponseEntity<ApiResponse<WorkspaceResponse>> getWorkspace(@PathVariable Long workspaceId) {
        ApiResponse<WorkspaceResponse> response = workspaceService.getWorkspace(workspaceId);
        return ApiResponse.of(response);
    }

    @PutMapping("/{workspaceId}")
    public ResponseEntity<ApiResponse<WorkspaceResponse>> updateWorkspace(@PathVariable Long workspaceId, @RequestBody WorkspaceUpdateRequest request) {
        ApiResponse<WorkspaceResponse> response = workspaceService.updateWorkspace(workspaceId, request);
        return ApiResponse.of(response);
    }

    @DeleteMapping("/{workspaceId}")
    public ResponseEntity<ApiResponse<Void>> deleteWorkspace(@PathVariable Long workspaceId) {
        ApiResponse<Void> response = workspaceService.deleteWorkspace(workspaceId);
        return ApiResponse.of(response);
    }
}