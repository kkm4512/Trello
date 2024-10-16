package com.example.trello.domain.attachment.service;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.domain.user.dto.AuthUser;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AttachmentService {
    ApiResponse<List<String>> uploads(AuthUser authUser, String card_id, List<MultipartFile> files);
    ApiResponse<List<String>> downloads(String card_id);
    ApiResponse<List<String>> deletes(String card_id);
}
