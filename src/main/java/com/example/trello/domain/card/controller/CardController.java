package com.example.trello.domain.card.controller;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.domain.card.dto.request.SaveCardRequest;
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
}
