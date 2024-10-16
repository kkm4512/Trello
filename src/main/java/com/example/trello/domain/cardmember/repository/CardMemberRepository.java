package com.example.trello.domain.cardmember.repository;

import com.example.trello.domain.cardmember.entity.CardMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardMemberRepository extends JpaRepository<CardMember, Long> {
    boolean existsByUserIdAndCardId(Long userId, Long cardId);

    List<CardMember> findByCardId(Long id);
}
