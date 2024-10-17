package com.example.trello.domain.cardlog.controller;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.domain.cardlog.dto.response.MemberLogResponse;
import com.example.trello.domain.cardlog.service.CardLogService;
import com.example.trello.domain.user.dto.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workspaces/{workspaceId}/boards/{boardsId}/lists/{listId}/cards/{cardId}")
@RequiredArgsConstructor
public class CardMemberLogController {
    private CardLogService cardLogService;

    @GetMapping("/member-logs")
    public ResponseEntity<ApiResponse<MemberLogResponse>> getMemberLog(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long workspaceId,
            @PathVariable Long boardsId,
            @PathVariable Long listId,
            @PathVariable Long cardId
    ) {
        ApiResponse<MemberLogResponse> apiResponse = cardLogService.getMemberLog(authUser, workspaceId, boardsId, listId, cardId);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
