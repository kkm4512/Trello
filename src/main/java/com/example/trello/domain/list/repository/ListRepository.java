package com.example.trello.domain.list.repository;

import com.example.trello.domain.list.entity.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRepository extends JpaRepository<List, Long> {
}
