package com.example.trello.domain.card.service;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.common.response.ApiResponseCardEnum;
import com.example.trello.common.response.ApiResponseEnum;
import com.example.trello.domain.board.repository.BoardRepository;
import com.example.trello.domain.card.dto.request.SaveCardRequest;
import com.example.trello.domain.card.dto.response.MemberInfo;
import com.example.trello.domain.card.dto.response.SaveCardResponse;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.card.entity.CardManager;
import com.example.trello.domain.card.repository.CardManagerRepository;
import com.example.trello.domain.card.repository.CardRepository;
import com.example.trello.domain.list.entity.BoardList;
import com.example.trello.domain.list.repository.ListRepository;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.repository.UserRepository;
import com.example.trello.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {
    private final UserRepository userRepository;
    private final CardManagerRepository cardManagerRepository;
    private final WorkspaceRepository workspaceRepository;
    private final BoardRepository boardRepository;
    private final ListRepository listRepository;
    private final CardRepository cardRepository;

    /* 카드 등록 */
    public ApiResponse<SaveCardResponse> saveCard(Long workspaceId, Long boardsId, Long listId, SaveCardRequest request) {
        ApiResponseEnum apiResponseEnum = ApiResponseCardEnum.CARD_SAVE_OK;
        boolean isWorkspace = workspaceRepository.existsById(workspaceId);
        if (isWorkspace) {
            throw new IllegalArgumentException("해당 워크 스페이스가 없습니다.");
        }

        boolean isBoard = boardRepository.existsById(boardsId);
        if (isBoard) {
            throw new IllegalArgumentException("해당 보더가 없습니다.");
        }

        BoardList list = listRepository.findById(listId).orElseThrow(
                () -> new IllegalArgumentException("해당 리스트가 없습니다."));

        Card card = cardRepository.save(new Card(request, list));

        List<MemberInfo> registeredMembers = new ArrayList<>();
        if (request.getMember() != null && !request.getMember().isEmpty()) {
            for (Long memberId : request.getMember()) {
                User member = userRepository.findById(memberId)
                        .orElseThrow(() -> new IllegalArgumentException("등록하려는 멤버가 없습니다."));
                CardManager cardManager = new CardManager(member, card);
                cardManagerRepository.save(cardManager);
                registeredMembers.add(new MemberInfo(member.getId(), member.getEmail()));
            }
        }
        ApiResponse<SaveCardResponse> apiResponse = new ApiResponse<>(apiResponseEnum, new SaveCardResponse(card,registeredMembers));
        return apiResponse;
    }
}
