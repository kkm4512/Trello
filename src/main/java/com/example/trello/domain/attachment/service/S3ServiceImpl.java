package com.example.trello.domain.attachment.service;

import com.example.trello.common.exception.CardException;
import com.example.trello.common.exception.FileException;
import com.example.trello.common.exception.UserException;
import com.example.trello.common.response.ApiResponse;
import com.example.trello.common.response.ApiResponseFileEnum;
import com.example.trello.domain.attachment.entity.Attachment;
import com.example.trello.domain.attachment.repository.AttachmentRepository;
import com.example.trello.domain.attachment.service.util.AttachmentFileService;
import com.example.trello.domain.attachment.service.util.AttachmentUtilService;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.card.repository.CardRepository;
import com.example.trello.domain.user.dto.AuthUser;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.example.trello.common.path.GlobalPath.*;
import static com.example.trello.common.response.ApiResponseCardEnum.CARD_NOT_FOUND;
import static com.example.trello.common.response.ApiResponseUserEnum.USER_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
@Primary
public class S3ServiceImpl implements AttachmentService {

    private final AttachmentUtilService utilService;
    private final AttachmentFileService fileService;

    private final AttachmentRepository attachmentRepository;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    private final S3Service s3Service;

    @Transactional
    @Override
    public ApiResponse<List<String>> uploads(AuthUser authUser, String card_id, List<MultipartFile> files) {
        List<String> data = new ArrayList<>();
        try {
            User user = userRepository.findById(authUser.getId()).orElseThrow(() -> new UserException(USER_NOT_FOUND));
            Card card = cardRepository.findById(Long.parseLong(card_id)).orElseThrow(() -> new CardException(CARD_NOT_FOUND));
            for ( MultipartFile file : files ) {
                Path filePath =  fileService.mkFilepath(card_id,file.getOriginalFilename());

                Attachment attachment = Attachment.builder()
                        .path(filePath.toString())
                        .originFileName(file.getOriginalFilename())
                        .user(user)
                        .card(card)
                        .build();
                attachmentRepository.save(attachment);
                s3Service.put(filePath,file);
                String fileName = utilService.getLastFileName(filePath.toString());
                System.out.println(fileName);
                data.add(fileName);
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new FileException(ApiResponseFileEnum.FILE_IO_ERROR);
        }
        return ApiResponse.of(ApiResponseFileEnum.FILE_OK,data);
    }

    @Override
    public ApiResponse<List<String>> downloads(String card_id,List<String> fileNames) {
        long start = System.currentTimeMillis();
        List<String> data = new ArrayList<>();
        try {
            List<Attachment> attachments = attachmentRepository.findByCardId(Long.parseLong(card_id));
            for (Attachment attachment : attachments) {
                for (String fileName : fileNames) {
                    if (attachment.getPath().equals(FILES + SEPARATOR + card_id + SEPARATOR + fileName)) {
                        data.add(S3_BASE_URL + SEPARATOR + attachment.getPath());
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            long end = System.currentTimeMillis();
            System.out.println(end - start);
        }

        return ApiResponse.of(ApiResponseFileEnum.FILE_OK,data);
    }

    @Override
    public ApiResponse<List<String>> deletes(String card_id,List<String> fileNames) {
        List<String> data = new ArrayList<>();
        try {
            List<Attachment> attachments = attachmentRepository.findByCardId(Long.parseLong(card_id));
            for (Attachment attachment : attachments) {
                for (String fileName : fileNames) {
                    if (attachment.getPath().equals(FILES + SEPARATOR + card_id + SEPARATOR + fileName)) {
                        data.add(fileName);
                        attachmentRepository.delete(attachment);
                        s3Service.delete(attachment.getPath());
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new FileException(ApiResponseFileEnum.FILE_NOT_FOUND);
        }
        return ApiResponse.of(ApiResponseFileEnum.FILE_OK,data);
    }

}
