package com.example.trello.domain.comment.controller;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.domain.card.dto.request.SaveCardRequest;
import com.example.trello.domain.cardmember.dto.response.SaveCardMemberResponse;
import com.example.trello.domain.comment.dto.request.PutCommentRequest;
import com.example.trello.domain.comment.dto.response.DeleteCommentResponse;
import com.example.trello.domain.comment.dto.response.PutCommentResponse;
import com.example.trello.domain.comment.dto.response.SaveCommentResponse;
import com.example.trello.domain.comment.service.CommentService;
import com.example.trello.domain.user.dto.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Comment", description = "김현")
@RestController
@RequestMapping("workspaces/{workspaceId}/boards/{boardsId}/lists/{listId}/cards/{cardId}")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "댓글 생성", description = "카드에 댓글을 생성합니다.")
    @PostMapping("/comments")
    public ResponseEntity<ApiResponse<SaveCommentResponse>> saveComment(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long workspaceId,
            @PathVariable Long boardsId,
            @PathVariable Long listId,
            @PathVariable Long cardId,
            @RequestBody SaveCardRequest request
    ) {
        ApiResponse<SaveCommentResponse> apiResponse = commentService.saveComment(authUser, workspaceId, boardsId, listId, cardId, request);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @Operation(summary = "댓글 수정", description = "카드에 댓글을 수정합니다.")
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse<PutCommentResponse>> updateComment(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long workspaceId,
            @PathVariable Long boardsId,
            @PathVariable Long listId,
            @PathVariable Long cardId,
            @PathVariable Long commentId,
            @RequestBody PutCommentRequest request
    ) {
        ApiResponse<PutCommentResponse> apiResponse = commentService.updateComment(authUser, workspaceId, boardsId, listId, cardId, commentId, request);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @Operation(summary = "댓글 삭제", description = "카드에 댓글을 삭제합니다.")
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse<DeleteCommentResponse>> deleteComment(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long workspaceId,
            @PathVariable Long boardsId,
            @PathVariable Long listId,
            @PathVariable Long cardId,
            @PathVariable Long commentId
    ) {
        ApiResponse<DeleteCommentResponse> apiResponse = commentService.deleteComment(authUser, workspaceId, boardsId, listId, cardId, commentId);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }
}
