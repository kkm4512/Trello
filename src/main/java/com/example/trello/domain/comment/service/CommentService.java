package com.example.trello.domain.comment.service;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.common.response.ApiResponseCardEnum;
import com.example.trello.common.response.ApiResponseEnum;
import com.example.trello.domain.board.repository.BoardRepository;
import com.example.trello.domain.card.dto.request.SaveCardRequest;
import com.example.trello.domain.card.dto.response.SaveCardResponse;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.card.repository.CardRepository;
import com.example.trello.domain.comment.dto.request.PutCommentRequest;
import com.example.trello.domain.comment.dto.response.DeleteCommentResponse;
import com.example.trello.domain.comment.dto.response.PutCommentResponse;
import com.example.trello.domain.comment.dto.response.SaveCommentResponse;
import com.example.trello.domain.comment.entity.Comment;
import com.example.trello.domain.comment.repository.CommentRepository;
import com.example.trello.domain.list.repository.ListRepository;
import com.example.trello.domain.user.repository.UserRepository;
import com.example.trello.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final WorkspaceRepository workspaceRepository;
    private final BoardRepository boardRepository;
    private final ListRepository listRepository;
    private final CardRepository cardRepository;

    /* 댓글 생성 */
    @Transactional
    public ApiResponse<SaveCommentResponse> saveComment(Long workspaceId, Long boardsId, Long listId, Long cardId, SaveCardRequest request) {
        boolean isWorkspace = workspaceRepository.existsById(workspaceId);
        boolean isBoard = boardRepository.existsById(boardsId);
        boolean list = listRepository.existsById(listId);
        if (!isWorkspace) {
            throw new IllegalArgumentException("해당 워크 스페이스가 없습니다.");
        }
        if (!isBoard) {
            throw new IllegalArgumentException("해당 보더가 없습니다.");
        }
        if (!list) {
            throw new IllegalArgumentException("해당 리스트가 없습니다.");
        }
        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new IllegalArgumentException("해당 카드가 없습니다."));

        // 유저 빠져있음
        Comment comment = new Comment(request, card);
        commentRepository.save(comment);

        ApiResponseEnum apiResponseEnum = ApiResponseCardEnum.CARD_SAVE_OK;
        ApiResponse<SaveCommentResponse> apiResponse = new ApiResponse<>(apiResponseEnum, new SaveCommentResponse(comment, card));
        return apiResponse;
    }

    /* 댓글 수정 */
    @Transactional
    public ApiResponse<PutCommentResponse> updateComment(Long workspaceId, Long boardsId, Long listId, Long cardId, Long commentId, PutCommentRequest request) {
        boolean isWorkspace = workspaceRepository.existsById(workspaceId);
        boolean isBoard = boardRepository.existsById(boardsId);
        boolean list = listRepository.existsById(listId);
        if (!isWorkspace) {
            throw new IllegalArgumentException("해당 워크 스페이스가 없습니다.");
        }
        if (!isBoard) {
            throw new IllegalArgumentException("해당 보더가 없습니다.");
        }
        if (!list) {
            throw new IllegalArgumentException("해당 리스트가 없습니다.");
        }
        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new IllegalArgumentException("해당 카드가 없습니다."));
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 없습니다."));

        comment.update(request);

        ApiResponseEnum apiResponseEnum = ApiResponseCardEnum.CARD_SAVE_OK;
        ApiResponse<PutCommentResponse> apiResponse = new ApiResponse<>(apiResponseEnum, new PutCommentResponse(comment, card));
        return apiResponse;
    }

    /* 댓글 삭제 */
    @Transactional
    public ApiResponse<DeleteCommentResponse> deleteComment(Long workspaceId, Long boardsId, Long listId, Long cardId, Long commentId) {
        boolean isWorkspace = workspaceRepository.existsById(workspaceId);
        boolean isBoard = boardRepository.existsById(boardsId);
        boolean list = listRepository.existsById(listId);
        boolean card = cardRepository.existsById(cardId);
        if (!isWorkspace) {
            throw new IllegalArgumentException("해당 워크 스페이스가 없습니다.");
        }
        if (!isBoard) {
            throw new IllegalArgumentException("해당 보더가 없습니다.");
        }
        if (!list) {
            throw new IllegalArgumentException("해당 리스트가 없습니다.");
        }
        if (!card) {
            throw new IllegalArgumentException("해당 카드가 없습니다.");
        }

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 없습니다."));

        commentRepository.delete(comment);

        ApiResponseEnum apiResponseEnum = ApiResponseCardEnum.CARD_SAVE_OK;
        ApiResponse<DeleteCommentResponse> apiResponse = new ApiResponse<>(apiResponseEnum, new DeleteCommentResponse(comment));
        return apiResponse;
    }
}
