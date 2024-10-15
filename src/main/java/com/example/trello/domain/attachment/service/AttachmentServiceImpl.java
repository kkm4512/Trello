package com.example.trello.domain.attachment.service;

import com.example.trello.common.exception.FileException;
import com.example.trello.common.exception.UserException;
import com.example.trello.common.response.ApiResponse;
import com.example.trello.common.response.ApiResponseFileEnum;
import com.example.trello.common.response.ApiResponseUserEnum;
import com.example.trello.domain.attachment.entity.Attachment;
import com.example.trello.domain.attachment.repository.AttachmentRepository;
import com.example.trello.domain.user.dto.AuthUser;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.trello.common.path.GlobalPath.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepository attachmentRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ApiResponse<List<String>> uploads(AuthUser authUser, String card_id, List<MultipartFile> files) {
        try {
            User user = userRepository.findById(authUser.getId()).orElseThrow(() -> new UserException(ApiResponseUserEnum.USER_NOT_FOUND));
            Path directory = Paths.get("files")
                    .resolve("cards")
                    .resolve(card_id);
            Files.createDirectories(directory);
            List<String> paths = new ArrayList<>();
            for ( MultipartFile file : files ) {
                // 파일이 존재할때만
                if (!(file == null || file.isEmpty())) {
                    String filename = UUID.randomUUID() + DELIMITER + file.getOriginalFilename();
                    Path filePath = Paths.get(directory.toString(),filename);
                    // DB저장
                    Attachment attachment = Attachment.builder()
                            .path(filePath.toString())
                            .user(user)
                            .build();
                    attachmentRepository.save(attachment);
                    // inputStream의 자원 누수를 방지하기위한 try - with - resources
                    try (InputStream inputStream = file.getInputStream()){
                        Files.copy(inputStream, filePath);
                        paths.add(filePath.toString());
                    } catch (IOException e){
                        log.error(e.getMessage(),e);
                        throw new FileException(ApiResponseFileEnum.FILE_IO_ERROR);
                    }
                }
            }
            return new ApiResponse<>(ApiResponseFileEnum.FILE_OK,paths);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new FileException(ApiResponseFileEnum.FILE_IO_ERROR);
        }
    }

    @Override
    public ApiResponse<List<String>> downloads(String card_id) {
        try {
            Path path = Paths.get("files")
                    .resolve("cards")
                    .resolve(card_id);
            List<String> data = Files.list(path)
                    .map(p -> BASE_URL +
                            SEPARATOR + path.toString() +
                            SEPARATOR + p.getFileName().toString())
                    .toList();
            return ApiResponse.of(ApiResponseFileEnum.FILE_OK,data);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new FileException(ApiResponseFileEnum.FILE_NOT_FOUND);
        }
    }

    @Override
    public ApiResponse<List<String>> deletes(String card_id) {
        try {
            Path path = Paths.get("files")
                    .resolve("cards")
                    .resolve(card_id);
            List<String> data = Files.list(path)
                    .peek(this::delete)
                    .map(String::valueOf)
                    .toList();
            return ApiResponse.of(ApiResponseFileEnum.FILE_OK,data);
        } catch (Exception e){
            throw new FileException(ApiResponseFileEnum.FILE_NOT_FOUND);
        }

    }

    private void delete(Path path) {
        try {
            Files.delete(path);
        } catch (Exception e) {
            throw new FileException(ApiResponseFileEnum.FILE_NOT_FOUND);
        }

    }
}
