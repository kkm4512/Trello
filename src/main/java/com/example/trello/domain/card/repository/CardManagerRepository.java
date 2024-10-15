package com.example.trello.domain.card.repository;

import com.example.trello.domain.card.entity.CardManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardManagerRepository extends JpaRepository<CardManager, Long> {
}
