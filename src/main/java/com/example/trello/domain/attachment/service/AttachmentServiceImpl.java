package com.example.trello.domain.attachment.service;

import com.example.trello.common.exception.CardException;
import com.example.trello.common.exception.FileException;
import com.example.trello.common.exception.UserException;
import com.example.trello.common.response.ApiResponse;
import com.example.trello.common.response.ApiResponseFileEnum;
import com.example.trello.domain.attachment.entity.Attachment;
import com.example.trello.domain.attachment.repository.AttachmentRepository;
import com.example.trello.domain.attachment.service.util.AttachmentDirectoryService;
import com.example.trello.domain.attachment.service.util.AttachmentFileService;
import com.example.trello.domain.attachment.service.util.AttachmentPathService;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.card.repository.CardRepository;
import com.example.trello.domain.user.dto.AuthUser;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.example.trello.common.path.GlobalPath.BASE_URL;
import static com.example.trello.common.path.GlobalPath.SEPARATOR;
import static com.example.trello.common.response.ApiResponseCardEnum.CARD_NOT_FOUND;
import static com.example.trello.common.response.ApiResponseFileEnum.*;
import static com.example.trello.common.response.ApiResponseUserEnum.USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {
    // Attachment 기능 관련 서비스
    private final AttachmentPathService pathService;
    private final AttachmentFileService fileService;
    private final AttachmentDirectoryService directoryService;

    // 그 외
    private final AttachmentRepository attachmentRepository;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ApiResponse<List<String>> uploads(AuthUser authUser, String card_id, List<MultipartFile> files) {
        List<String> fileNames = new ArrayList<>();
        try {
            User user = userRepository.findById(authUser.getId()).orElseThrow(() -> new UserException(USER_NOT_FOUND));
            Card card = cardRepository.findById(Long.parseLong(card_id)).orElseThrow(() -> new CardException(CARD_NOT_FOUND));
            Path directory = pathService.mkFilesCardsPath(card_id);
            directoryService.mkdir(directory);
            for ( MultipartFile file : files ) {
                // 파일이 존재할때만
                if (!(file == null || file.isEmpty())) {
                    String filename = fileService.mkFilename(file.getOriginalFilename());
                    Path filePath = pathService.mkPath(directory, filename);
                    // DB저장
                    Attachment attachment = Attachment.builder()
                            .path(filePath.toString())
                            .originFileName(file.getOriginalFilename())
                            .user(user)
                            .card(card)
                            .build();
                    attachmentRepository.save(attachment);
                    fileService.mkFile(filePath, file);
                    fileNames.add(file.getOriginalFilename());
                }
            }
            return new ApiResponse<>(ApiResponseFileEnum.FILE_OK,fileNames);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new FileException(FILE_IO_ERROR);
        }
    }

    @Override
    public ApiResponse<List<String>> downloads(String card_id) {
        List<String> data = new ArrayList<>();
        try {
            Path path = pathService.mkFilesCardsPath(card_id);
             data = Files.list(path)
                    .map(p -> BASE_URL +
                            SEPARATOR + path.toString() +
                            SEPARATOR + p.getFileName().toString())
                    .toList();
            return ApiResponse.of(FILE_OK,data);
        } catch (Exception e) {
            if (data.isEmpty()) {
                return ApiResponse.of(FILE_OK,data);
            }
            log.error(e.getMessage(),e);
            throw new FileException(FILE_NOT_FOUND);
        }
    }

    @Override
    public ApiResponse<List<String>> deletes(String card_id) {
        try {
            Path path = pathService.mkFilesCardsPath(card_id);
            List<String> data = Files.list(path)
                    .peek(this::delete)
                    .map(String::valueOf)
                    .toList();
            return ApiResponse.of(FILE_OK,data);
        } catch (Exception e){
            throw new FileException(FILE_NOT_FOUND);
        }

    }

    private void delete(Path path) {
        try {
            Files.delete(path);
        } catch (Exception e) {
            throw new FileException(FILE_NOT_FOUND);
        }

    }
}
