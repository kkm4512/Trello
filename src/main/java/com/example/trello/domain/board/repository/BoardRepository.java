package com.example.trello.domain.board.repository;

import com.example.trello.domain.board.entity.Board;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAllByWorkspaceId(Long workspaceId);

    Optional<Board> findByIdAndWorkspaceId(Long boardId, Long workspaceId);

}
