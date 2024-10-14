package com.example.trello.domain.attachment.service;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

public interface AttachmentService {
    List<String> uploads(Path path, List<MultipartFile> files);
    List<String> getFiles(Path path);
    void deletes(Path path);
    void delete(Path path);
}
