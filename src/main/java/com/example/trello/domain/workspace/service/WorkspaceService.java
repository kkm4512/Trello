package com.example.trello.domain.workspace.service;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.common.response.ApiRasponseWorkspaceEnum;
import com.example.trello.domain.workspace.dto.request.WorkspaceCreateRequest;
import com.example.trello.domain.workspace.dto.request.WorkspaceUpdateRequest;
import com.example.trello.domain.workspace.dto.response.WorkspaceResponse;
import com.example.trello.domain.workspace.entity.Workspace;
import com.example.trello.domain.workspace.repository.WorkspaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;

    public WorkspaceService(WorkspaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
    }

    // 워크스페이스 생성
    public ApiResponse<WorkspaceResponse> createWorkspace(WorkspaceCreateRequest request) {
        try {
            Workspace workspace = new Workspace();
            workspace.setTitle(request.getTitle());
            workspace.setDescription(request.getDescription());
            workspace.setCreatedAt(LocalDateTime.now());
            workspace.setUpdatedAt(LocalDateTime.now());
            workspaceRepository.save(workspace);

            WorkspaceResponse response = new WorkspaceResponse(
                    workspace.getId(),
                    workspace.getTitle(),
                    workspace.getDescription(),
                    workspace.getCreatedAt(),
                    workspace.getUpdatedAt());

            return ApiResponse.of(ApiRasponseWorkspaceEnum.WORKSPACE_CREATE_SUCCESS, response);
        } catch (Exception e) {
            return ApiResponse.of(ApiRasponseWorkspaceEnum.WORKSPACE_CREATE_FAIL, null);
        }
    }

    // 워크스페이스 조회
    public ApiResponse<WorkspaceResponse> getWorkspace(Long workspaceId) {
        try {
            Workspace workspace = workspaceRepository.findById(workspaceId)
                    .orElseThrow(() -> new RuntimeException(ApiRasponseWorkspaceEnum.WORKSPACE_NOT_FOUND.getMessage()));
            WorkspaceResponse response = new WorkspaceResponse(workspace.getId(), workspace.getTitle(), workspace.getDescription(), workspace.getCreatedAt(), workspace.getUpdatedAt());
            return ApiResponse.of(ApiRasponseWorkspaceEnum.WORKSPACE_READ_SUCCESS, response);
        } catch (Exception e) {
            return ApiResponse.of(ApiRasponseWorkspaceEnum.WORKSPACE_READ_FAIL, null);
        }
    }

    // 워크스페이스 수정
    public ApiResponse<WorkspaceResponse> updateWorkspace(Long workspaceId, WorkspaceUpdateRequest request) {
        try {
            Workspace workspace = workspaceRepository.findById(workspaceId)
                    .orElseThrow(() -> new RuntimeException(ApiRasponseWorkspaceEnum.WORKSPACE_NOT_FOUND.getMessage()));
            workspace.setTitle(request.getTitle());
            workspace.setDescription(request.getDescription());
            workspace.setUpdatedAt(LocalDateTime.now());
            workspaceRepository.save(workspace);
            WorkspaceResponse response = new WorkspaceResponse(workspace.getId(), workspace.getTitle(), workspace.getDescription(), workspace.getCreatedAt(), workspace.getUpdatedAt());
            return ApiResponse.of(ApiRasponseWorkspaceEnum.WORKSPACE_UPDATE_SUCCESS, response);
        } catch (Exception e) {
            return ApiResponse.of(ApiRasponseWorkspaceEnum.WORKSPACE_UPDATE_FAIL, null);
        }
    }

    // 워크스페이스 삭제
    public ApiResponse<Void> deleteWorkspace(Long workspaceId) {
        try {
            Workspace workspace = workspaceRepository.findById(workspaceId)
                    .orElseThrow(() -> new IllegalArgumentException(ApiRasponseWorkspaceEnum.WORKSPACE_NOT_FOUND.getMessage()));
            workspaceRepository.delete(workspace);
            return ApiResponse.of(ApiRasponseWorkspaceEnum.WORKSPACE_DELETE_SUCCESS, null);
        } catch (IllegalArgumentException e) {
            return ApiResponse.of(ApiRasponseWorkspaceEnum.WORKSPACE_NOT_FOUND, null);
        } catch (Exception e) {
            return ApiResponse.of(ApiRasponseWorkspaceEnum.WORKSPACE_DELETE_FAIL, null);
        }
    }

}