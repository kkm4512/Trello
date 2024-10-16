package com.example.trello.domain.user.repository;

import com.example.trello.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT email FROM users u WHERE u.email = :email AND u.deleted = true", nativeQuery = true)
    Optional<Object> findDeletedEmail(String email);

    @Query(value = "UPDATE Users u SET u.deleted = true WHERE u.id = :userId", nativeQuery = true)
    void updatedeleted(Long userId);
}
