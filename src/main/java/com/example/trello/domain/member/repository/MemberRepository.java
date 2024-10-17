package com.example.trello.domain.member.repository;

import com.example.trello.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 특정 워크스페이스에 속한 모든 멤버 조회
    List<Member> findAllByWorkspaceId(Long workspaceId);

    // 워크스페이스와 멤버 ID로 멤버 조회
    Optional<Member> findByIdAndWorkspaceId(Long memberId, Long workspaceId);

    // 유저 ID와 워크스페이스 ID로 멤버 조회
    Optional<Member> findByUserIdAndWorkspaceId(Long userId, Long workspaceId);

    // 유저 ID와 워크스페이스 ID로 멤버 존재 여부 확인
    boolean existsByUserIdAndWorkspaceId(Long userId, Long workspaceId);

    // 멤버 ID와 워크스페이스 ID로 멤버 존재 여부 확인
    boolean existsByIdAndWorkspaceId(Long memberId, Long workspaceId);

    Optional<Member> findByUserId(Long userId);
}
