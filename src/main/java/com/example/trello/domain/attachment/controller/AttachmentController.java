package com.example.trello.domain.attachment.controller;

import com.example.trello.common.exception.UserException;
import com.example.trello.common.response.ApiResponse;
import com.example.trello.domain.attachment.service.AttachmentService;
import com.example.trello.domain.attachment.validate.FileValidate;
import com.example.trello.domain.member.entity.Member;
import com.example.trello.domain.member.repository.MemberRepository;
import com.example.trello.domain.user.dto.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.example.trello.common.response.ApiResponseUserEnum.USER_NOT_FOUND;

@Tag(name = "Attachment", description = "김경민")
@RequiredArgsConstructor
@RestController
@Slf4j(topic = "AttachmentController")
@RequestMapping("/api/cards")
public class AttachmentController {
    private final AttachmentService attachmentService;
    private final MemberRepository memberRepository;

    @Operation(summary = "첨부파일 다운로드", description = "카드에 첨부된 파일을 다운로드합니다.")
    @GetMapping("/{card_id}/files")
    public ResponseEntity<ApiResponse<List<String>>> getAttachment(
            @PathVariable String card_id,
            @RequestParam List<String> fileNames
    ) {
        ApiResponse<List<String>> apiResponse = attachmentService.downloads(card_id,fileNames);
        return ApiResponse.of(apiResponse);
    }

    @Operation(summary = "첨부파일 업로드", description = "카드에 파일을 첨부합니다.")
    @PostMapping("/{card_id}/files")
    public ResponseEntity<ApiResponse<List<String>>> uploads(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable String card_id,
            @RequestPart(value = "files") List<MultipartFile> files
    ) {
        Member user = memberRepository.findByUserId(authUser.getId()).orElseThrow(() -> new UserException(USER_NOT_FOUND));
        FileValidate.isRole(user);
        FileValidate.isFormatFile(files);
        FileValidate.isSizeBigFile(files);
        ApiResponse<List<String>> apiResponse = attachmentService.uploads(
                authUser,
                card_id,
                files
        );
        return ApiResponse.of(apiResponse);
    }

    @Operation(summary = "첨부파일 삭제", description = "카드에 첨부된 파일을 삭제합니다.")
    @DeleteMapping("/{card_id}/files")
    public ResponseEntity<ApiResponse<List<String>>> deletes(
            @PathVariable String card_id,
            @RequestParam List<String> fileNames
    ) {
        ApiResponse<List<String>> apiResponse = attachmentService.deletes(card_id,fileNames);
        return ApiResponse.of(apiResponse);
    }
}
