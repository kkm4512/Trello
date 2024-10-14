package com.example.trello.domain.comment.controller;

import com.example.trello.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("workspaces/{workspaceId}/boards/{boardsId}/cards/{cardId}/")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
}
