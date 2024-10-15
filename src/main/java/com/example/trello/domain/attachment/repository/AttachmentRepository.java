package com.example.trello.domain.attachment.repository;

import com.example.trello.domain.attachment.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}
