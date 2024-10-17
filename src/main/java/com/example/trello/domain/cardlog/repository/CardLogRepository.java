package com.example.trello.domain.cardlog.repository;

import com.example.trello.domain.cardlog.entity.CardLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardLogRepository extends JpaRepository<CardLog, Long> {
    List<CardLog> findByCardId(Long cardId);
}
