package com.example.trello.domain.workspace.service;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.domain.user.dto.AuthUser;
import com.example.trello.domain.workspace.dto.request.WorkspaceRequest;
import com.example.trello.domain.workspace.dto.response.WorkspaceListResponse;
import com.example.trello.domain.workspace.dto.response.WorkspaceResponse;

import java.util.List;

public interface WorkspaceService {

    ApiResponse<WorkspaceResponse> createWorkspace(AuthUser authUser, WorkspaceRequest request);

    ApiResponse<List<WorkspaceListResponse>> getUserWorkspaces(AuthUser authUser);

    ApiResponse<WorkspaceResponse> updateWorkspace(AuthUser authUser, Long workspaceId, WorkspaceRequest request);

    ApiResponse<Void> deleteWorkspace(AuthUser authUser, Long workspaceId);

}
