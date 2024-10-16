package com.example.trello.domain.workspace.service;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.domain.user.dto.AuthUser;
import com.example.trello.domain.workspace.dto.request.WorkspaceCreateRequest;
import com.example.trello.domain.workspace.dto.request.WorkspaceUpdateRequest;
import com.example.trello.domain.workspace.dto.response.WorkspaceCreateResponse;
import com.example.trello.domain.workspace.dto.response.WorkspaceGetUserResponse;
import com.example.trello.domain.workspace.dto.response.WorkspaceUpdateResponse;

import java.util.List;

public interface WorkspaceService {

    ApiResponse<WorkspaceCreateResponse> createWorkspace(AuthUser authUser, WorkspaceCreateRequest request);

    ApiResponse<List<WorkspaceGetUserResponse>> getUserWorkspaces(AuthUser authUser);

    ApiResponse<WorkspaceUpdateResponse> updateWorkspace(AuthUser authUser, Long workspaceId, WorkspaceUpdateRequest request);

    ApiResponse<Void> deleteWorkspace(AuthUser authUser, Long workspaceId);

}
