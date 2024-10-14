package com.example.trello.domain.card.controller;

import com.example.trello.domain.card.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("workspaces/{workspaceId}/boards/{boardsId}")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;
}
