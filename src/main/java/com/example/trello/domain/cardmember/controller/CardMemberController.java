package com.example.trello.domain.cardmember.controller;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.domain.cardmember.dto.request.DeleteCardMemberRequest;
import com.example.trello.domain.cardmember.dto.request.SaveCardMemberRequest;
import com.example.trello.domain.cardmember.dto.response.DeleteCardMemberResponse;
import com.example.trello.domain.cardmember.dto.response.SaveCardMemberResponse;
import com.example.trello.domain.cardmember.service.CardMemberService;
import com.example.trello.domain.user.dto.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "CardMember", description = "김현")
@RestController
@RequestMapping("/workspaces/{workspaceId}/boards/{boardsId}/lists/{listId}/cards/{cardId}")
@RequiredArgsConstructor
public class CardMemberController {
    private final CardMemberService cardMemberService;

    @Operation(summary = "카드 멤버 추가", description = "카드에 멤버를 추가합니다.")
    @PostMapping("/member")
    public ResponseEntity<ApiResponse<SaveCardMemberResponse>> saveCardMember(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long workspaceId,
            @PathVariable Long boardsId,
            @PathVariable Long listId,
            @PathVariable Long cardId,
            @RequestBody SaveCardMemberRequest request
    ) {
        ApiResponse<SaveCardMemberResponse> apiResponse = cardMemberService.saveCardMember(authUser, workspaceId, boardsId, listId, cardId, request);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @Operation(summary = "카드 멤버 삭제", description = "카드에 멤버를 삭제합니다.")
    @DeleteMapping("/member")
    public ResponseEntity<ApiResponse<DeleteCardMemberResponse>> deleteCardMember(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long workspaceId,
            @PathVariable Long boardsId,
            @PathVariable Long listId,
            @PathVariable Long cardId,
            @RequestBody DeleteCardMemberRequest request
    ) {
        ApiResponse<DeleteCardMemberResponse> apiResponse = cardMemberService.deleteCardMember(authUser, workspaceId, boardsId, listId, cardId, request);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
