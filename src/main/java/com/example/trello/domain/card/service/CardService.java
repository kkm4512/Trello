package com.example.trello.domain.card.service;

import com.example.trello.common.annotation.CardChangeSlack;
import com.example.trello.common.response.ApiResponse;
import com.example.trello.common.response.ApiResponseCardEnum;
import com.example.trello.common.response.ApiResponseEnum;
import com.example.trello.domain.board.repository.BoardRepository;
import com.example.trello.domain.card.dto.request.PutCardRequest;
import com.example.trello.domain.card.dto.request.SaveCardRequest;
import com.example.trello.domain.card.dto.request.SearchCardRequest;
import com.example.trello.domain.card.dto.response.SearchCardResponse;
import com.example.trello.domain.card.dto.response.GetCardResponse;
import com.example.trello.domain.card.dto.response.PutCardResponse;
import com.example.trello.domain.card.dto.response.SaveCardResponse;
import com.example.trello.domain.card.dto.response.deleteResponse;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.card.repository.CardRepository;
import com.example.trello.domain.list.entity.BoardList;
import com.example.trello.domain.list.repository.ListRepository;
import com.example.trello.domain.user.repository.UserRepository;
import com.example.trello.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardService {
    private final UserRepository userRepository;
    private final WorkspaceRepository workspaceRepository;
    private final BoardRepository boardRepository;
    private final ListRepository listRepository;
    private final CardRepository cardRepository;

    /* 카드 등록 */
    @Transactional
public ApiResponse<SaveCardResponse> saveCard(Long workspaceId, Long boardsId, Long listId, SaveCardRequest request) {
        boolean isWorkspace = workspaceRepository.existsById(workspaceId);
        if (!isWorkspace) {
            throw new IllegalArgumentException("해당 워크 스페이스가 없습니다.");
        }

        boolean isBoard = boardRepository.existsById(boardsId);
        if (!isBoard) {
            throw new IllegalArgumentException("해당 보더가 없습니다.");
        }

        BoardList list = listRepository.findById(listId).orElseThrow(
                () -> new IllegalArgumentException("해당 리스트가 없습니다."));

        Card card = cardRepository.save(new Card(request, list));

        ApiResponseEnum apiResponseEnum = ApiResponseCardEnum.CARD_SAVE_OK;
        ApiResponse<SaveCardResponse> apiResponse = new ApiResponse<>(apiResponseEnum, new SaveCardResponse(card));
        return apiResponse;
    }

    /* 카드 수정 */
    @CardChangeSlack
    @Transactional
    public ApiResponse<PutCardResponse> updateCard(Long workspaceId, Long boardsId, Long listId, Long cardId, PutCardRequest request) {
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

        card.update(request);

        ApiResponseEnum apiResponseEnum = ApiResponseCardEnum.CARD_SAVE_OK;
        ApiResponse<PutCardResponse> apiResponse = new ApiResponse<>(apiResponseEnum, new PutCardResponse(card));
        return apiResponse;
    }

    /* 카드 상세 조회 */
    public ApiResponse<GetCardResponse> getCard(Long workspaceId, Long boardsId, Long listId, Long cardId) {
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

        ApiResponseEnum apiResponseEnum = ApiResponseCardEnum.CARD_SAVE_OK;
        ApiResponse<GetCardResponse> apiResponse = new ApiResponse<>(apiResponseEnum, new GetCardResponse(card));
        return apiResponse;
    }

    /* 카드 삭제 */
    @Transactional
    public ApiResponse<deleteResponse> deleteCard(Long workspaceId, Long boardsId, Long listId, Long cardId) {
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

        cardRepository.delete(card);

        ApiResponseEnum apiResponseEnum = ApiResponseCardEnum.CARD_SEARCH_OK;
        ApiResponse<deleteResponse> apiResponse = new ApiResponse<>(apiResponseEnum, new deleteResponse(card));
        return apiResponse;
    }


    public ApiResponse<Page<SearchCardResponse>> searchCard(Long workspaceId, Long boardsId, SearchCardRequest request, int page, int size) {
        boolean isWorkspace = workspaceRepository.existsById(workspaceId);
        boolean isBoard = boardRepository.existsById(boardsId);
        if (!isWorkspace) {
            throw new IllegalArgumentException("해당 워크 스페이스가 없습니다.");
        }
        if (!isBoard) {
            throw new IllegalArgumentException("해당 보더가 없습니다.");
        }

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<SearchCardResponse> cards = cardRepository.searchCards(request, pageable);

        ApiResponseEnum apiResponseEnum = ApiResponseCardEnum.CARD_SEARCH_OK;
        return new ApiResponse<>(apiResponseEnum, cards);
    }
}
