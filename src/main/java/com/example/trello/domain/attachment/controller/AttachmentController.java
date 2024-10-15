package com.example.trello.domain.attachment.controller;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.domain.attachment.service.AttachmentService;
import com.example.trello.domain.attachment.validate.FileValidate;
import com.example.trello.domain.user.dto.AuthUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j(topic = "AttachmentController")
@RequestMapping("/api/cards")
public class AttachmentController {
    private final AttachmentService attachmentService;

    @GetMapping("/{card_id}/files")
    public ResponseEntity<ApiResponse<List<String>>> getAttachment(
            @PathVariable String card_id
    ) {
        ApiResponse<List<String>> apiResponse = attachmentService.downloads(card_id);
        return ApiResponse.of(apiResponse);
    }

    @PostMapping("/{card_id}/files")
    public ResponseEntity<ApiResponse<List<String>>> uploads(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable String card_id,
            @RequestPart(value = "files") List<MultipartFile> files
    ) {
        FileValidate.isFormatFile(files);
        FileValidate.isSizeBigFile(files);
        ApiResponse<List<String>> apiResponse = attachmentService.uploads(
                authUser,
                card_id,
                files
        );
        return ApiResponse.of(apiResponse);
    }

    @DeleteMapping("/{card_id}/files")
    public ResponseEntity<ApiResponse<List<String>>> deletes(
            @PathVariable String card_id
    ) {
        ApiResponse<List<String>> apiResponse = attachmentService.deletes(card_id);
        return ApiResponse.of(apiResponse);
    }
}
