package com.example.trello.domain.attachment.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.trello.common.exception.CardException;
import com.example.trello.common.exception.FileException;
import com.example.trello.common.exception.UserException;
import com.example.trello.common.response.ApiResponse;
import com.example.trello.common.response.ApiResponseFileEnum;
import com.example.trello.domain.attachment.entity.Attachment;
import com.example.trello.domain.attachment.repository.AttachmentRepository;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.card.repository.CardRepository;
import com.example.trello.domain.user.dto.AuthUser;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.trello.common.path.GlobalPath.*;
import static com.example.trello.common.response.ApiResponseCardEnum.CARD_NOT_FOUND;
import static com.example.trello.common.response.ApiResponseUserEnum.USER_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
@Primary
public class S3ServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    @Override
    public ApiResponse<List<String>> uploads(AuthUser authUser, String card_id, List<MultipartFile> files) {
        try {
            for ( MultipartFile file : files ) {
                String fileName =  FILES +
                        SEPARATOR +
                        authUser.getId() +
                        SEPARATOR +
                        UUID.randomUUID() +
                        DELIMITER +
                        file.getOriginalFilename();

                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentType(file.getContentType());
                metadata.setContentLength(file.getSize());
                amazonS3Client.putObject(bucket,fileName,file.getInputStream(),metadata);
                User user = userRepository.findById(authUser.getId()).orElseThrow(() -> new UserException(USER_NOT_FOUND));
                Card card = cardRepository.findById(Long.parseLong(card_id)).orElseThrow(() -> new CardException(CARD_NOT_FOUND));
                Attachment attachment = Attachment.builder()
                        .path(fileName)
                        .originFileName(file.getOriginalFilename())
                        .user(user)
                        .card(card)
                        .build();
                attachmentRepository.save(attachment);
            }
        } catch (Exception e) {
            throw new FileException(ApiResponseFileEnum.FILE_IO_ERROR);
        }
        List<String> data = new ArrayList<>();
        return new ApiResponse<>(ApiResponseFileEnum.FILE_OK,data);
    }

    @Override
    public ApiResponse<List<String>> downloads(String card_id) {
        List<String> data = new ArrayList<>();
        List<Attachment> attachments = attachmentRepository.findByCardId(Long.parseLong(card_id));
        for (Attachment attachment : attachments) {
            data.add(
                    S3_BASE_URL + SEPARATOR + attachment.getPath()
            );
        }
        return new ApiResponse<>(ApiResponseFileEnum.FILE_OK,data);
    }

    @Override
    public ApiResponse<List<String>> deletes(String card_id) {
        List<String> data = new ArrayList<>();
        List<Attachment> attachments = attachmentRepository.findByCardId(Long.parseLong(card_id));
        for ( Attachment attachment : attachments ) {
            amazonS3Client.deleteObject(bucket,attachment.getPath());
            attachmentRepository.delete(attachment);
            data.add(attachment.getOriginFileName());
        }
        return new ApiResponse<>(ApiResponseFileEnum.FILE_OK,data);
    }
}
