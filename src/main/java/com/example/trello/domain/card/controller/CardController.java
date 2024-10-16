package com.example.trello.domain.card.controller;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.domain.card.dto.request.PutCardRequest;
import com.example.trello.domain.card.dto.request.SaveCardRequest;
import com.example.trello.domain.card.dto.response.GetCardResponse;
import com.example.trello.domain.card.dto.response.PutCardResponse;
import com.example.trello.domain.card.dto.response.SaveCardResponse;
import com.example.trello.domain.card.dto.response.deleteResponse;
import com.example.trello.domain.card.service.CardService;
import com.example.trello.domain.user.dto.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workspaces/{workspaceId}/boards/{boardsId}/lists/{listId}")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping("/cards")
    public ResponseEntity<ApiResponse<SaveCardResponse>> saveCard(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long workspaceId,
            @PathVariable Long boardsId,
            @PathVariable Long listId,
            @RequestBody SaveCardRequest request
            ) {
        ApiResponse<SaveCardResponse> apiResponse = cardService.saveCard(authUser, workspaceId, boardsId, listId, request);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @PutMapping("/cards/{cardId}")
    public ResponseEntity<ApiResponse<PutCardResponse>> updateCard(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long workspaceId,
            @PathVariable Long boardsId,
            @PathVariable Long listId,
            @PathVariable Long cardId,
            @RequestBody PutCardRequest request
    ) {
        ApiResponse<PutCardResponse> apiResponse = cardService.updateCard(authUser, workspaceId, boardsId, listId, cardId, request);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @GetMapping("/cards/{cardId}")
    public ResponseEntity<ApiResponse<GetCardResponse>> getCard(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long workspaceId,
            @PathVariable Long boardsId,
            @PathVariable Long listId,
            @PathVariable Long cardId
    ) {
        ApiResponse<GetCardResponse> apiResponse = cardService.getCard(authUser, workspaceId, boardsId, listId, cardId);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @DeleteMapping("/cards/{cardId}")
    public ResponseEntity<ApiResponse<deleteResponse>> deleteCard(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long workspaceId,
            @PathVariable Long boardsId,
            @PathVariable Long listId,
            @PathVariable Long cardId
    ) {
        ApiResponse<deleteResponse> apiResponse = cardService.deleteCard(authUser, workspaceId, boardsId, listId, cardId);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
