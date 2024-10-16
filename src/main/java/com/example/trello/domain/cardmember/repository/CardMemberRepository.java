package com.example.trello.domain.cardmember.repository;

import com.example.trello.domain.cardmember.entity.CardMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardMemberRepository extends JpaRepository<CardMember, Long> {
    boolean existsByMemberIdAndCardId(Long memberId, Long cardId);
}
