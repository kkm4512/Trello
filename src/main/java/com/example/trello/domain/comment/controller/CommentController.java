package com.example.trello.domain.comment.controller;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.domain.card.dto.request.SaveCardRequest;
import com.example.trello.domain.cardmember.dto.response.SaveCardMemberResponse;
import com.example.trello.domain.comment.dto.request.PutCommentRequest;
import com.example.trello.domain.comment.dto.response.DeleteCommentResponse;
import com.example.trello.domain.comment.dto.response.PutCommentResponse;
import com.example.trello.domain.comment.dto.response.SaveCommentResponse;
import com.example.trello.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("workspaces/{workspaceId}/boards/{boardsId}/lists/{listId}/cards/{cardId}")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comments")
    public ResponseEntity<ApiResponse<SaveCommentResponse>> saveComment(
            @PathVariable Long workspaceId,
            @PathVariable Long boardsId,
            @PathVariable Long listId,
            @PathVariable Long cardId,
            @RequestBody SaveCardRequest request
    ) {
        ApiResponse<SaveCommentResponse> apiResponse = commentService.saveComment(workspaceId, boardsId, listId, cardId, request);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse<PutCommentResponse>> updateComment(
            @PathVariable Long workspaceId,
            @PathVariable Long boardsId,
            @PathVariable Long listId,
            @PathVariable Long cardId,
            @PathVariable Long commentId,
            @RequestBody PutCommentRequest request
    ) {
        ApiResponse<PutCommentResponse> apiResponse = commentService.updateComment(workspaceId, boardsId, listId, cardId, commentId, request);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse<DeleteCommentResponse>> deleteComment(
            @PathVariable Long workspaceId,
            @PathVariable Long boardsId,
            @PathVariable Long listId,
            @PathVariable Long cardId,
            @PathVariable Long commentId
    ) {
        ApiResponse<DeleteCommentResponse> apiResponse = commentService.deleteComment(workspaceId, boardsId, listId, cardId, commentId);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
