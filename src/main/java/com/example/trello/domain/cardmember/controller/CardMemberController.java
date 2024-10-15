package com.example.trello.domain.cardmember.controller;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.domain.cardmember.dto.request.DeleteCardMemberRequest;
import com.example.trello.domain.cardmember.dto.request.SaveCardMemberRequest;
import com.example.trello.domain.cardmember.dto.response.DeleteCardMemberResponse;
import com.example.trello.domain.cardmember.dto.response.SaveCardMemberResponse;
import com.example.trello.domain.cardmember.service.CardMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workspaces/{workspaceId}/boards/{boardsId}/lists/{listId}/cards/{cardId}")
@RequiredArgsConstructor
public class CardMemberController {
    private final CardMemberService cardMemberService;

    @PostMapping("/member")
    public ResponseEntity<ApiResponse<SaveCardMemberResponse>> saveCardMember(
            @PathVariable Long workspaceId,
            @PathVariable Long boardsId,
            @PathVariable Long listId,
            @PathVariable Long cardId,
            @RequestBody SaveCardMemberRequest request
    ) {
        ApiResponse<SaveCardMemberResponse> apiResponse = cardMemberService.saveCardMember(workspaceId, boardsId, listId, cardId, request);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @DeleteMapping("/member")
    public ResponseEntity<ApiResponse<DeleteCardMemberResponse>> deleteCardMember(
            @PathVariable Long workspaceId,
            @PathVariable Long boardsId,
            @PathVariable Long listId,
            @PathVariable Long cardId,
            @RequestBody DeleteCardMemberRequest request
    ) {
        ApiResponse<DeleteCardMemberResponse> apiResponse = cardMemberService.deleteCardMember(workspaceId, boardsId, listId, cardId, request);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
