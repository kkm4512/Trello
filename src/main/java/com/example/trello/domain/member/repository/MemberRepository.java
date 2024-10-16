package com.example.trello.domain.member.repository;

import com.example.trello.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findAllByWorkspaceId(Long workspaceId);

    Optional<Member> findByIdAndWorkspaceId(Long memberId, Long workspaceId);

}
