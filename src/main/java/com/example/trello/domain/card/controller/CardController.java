package com.example.trello.domain.card.controller;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.domain.card.dto.request.PutCardRequest;
import com.example.trello.domain.card.dto.request.SaveCardRequest;
import com.example.trello.domain.card.dto.response.GetCardResponse;
import com.example.trello.domain.card.dto.response.PutCardResponse;
import com.example.trello.domain.card.dto.response.SaveCardResponse;
import com.example.trello.domain.card.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workspaces/{workspaceId}/boards/{boardsId}/lists/{listId}")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping("/cards")
    public ResponseEntity<ApiResponse<SaveCardResponse>> saveCard(
            @PathVariable Long workspaceId,
            @PathVariable Long boardsId,
            @PathVariable Long listId,
            @RequestBody SaveCardRequest request
            ) {
        ApiResponse<SaveCardResponse> apiResponse = cardService.saveCard(workspaceId, boardsId, listId, request);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @PutMapping("/cards/{cardId}")
    public ResponseEntity<ApiResponse<PutCardResponse>> updateCard(
            @PathVariable Long workspaceId,
            @PathVariable Long boardsId,
            @PathVariable Long listId,
            @PathVariable Long cardId,
            @RequestBody PutCardRequest request
    ) {
        ApiResponse<PutCardResponse> apiResponse = cardService.updateCard(workspaceId, boardsId, listId, cardId, request);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @GetMapping("/cards/{cardId}")
    public ResponseEntity<ApiResponse<GetCardResponse>> getCard(
            @PathVariable Long workspaceId,
            @PathVariable Long boardsId,
            @PathVariable Long listId,
            @PathVariable Long cardId
    ) {
        ApiResponse<GetCardResponse> apiResponse = cardService.getCard(workspaceId, boardsId, listId, cardId);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
