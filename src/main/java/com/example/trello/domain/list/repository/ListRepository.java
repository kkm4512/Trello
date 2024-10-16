package com.example.trello.domain.list.repository;

import com.example.trello.domain.list.entity.BoardList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListRepository extends JpaRepository<BoardList, Long> {
    List<BoardList> findByBoardId(Long boardId);
}
