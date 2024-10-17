package com.example.trello.domain.workspace.repository;


import com.example.trello.domain.workspace.entity.Workspace;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
    boolean existsById(@NotNull Long id);
    List<Workspace> findByMembersUserId(Long userId);
}
