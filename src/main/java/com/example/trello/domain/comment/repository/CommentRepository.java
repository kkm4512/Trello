package com.example.trello.domain.comment.repository;

import com.example.trello.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByCardId(Long cardId);
    Optional<Comment> findByIdAndUserId(Long commentId, Long userId);
}
