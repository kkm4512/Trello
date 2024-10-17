package com.example.trello.domain.cardlog.service;

import com.example.trello.common.exception.*;
import com.example.trello.common.response.ApiResponse;
import com.example.trello.common.response.ApiResponseCardMemberEnum;
import com.example.trello.common.response.ApiResponseEnum;
import com.example.trello.domain.board.repository.BoardRepository;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.card.repository.CardRepository;
import com.example.trello.domain.cardlog.dto.CardMemberLogInfo;
import com.example.trello.domain.cardlog.dto.response.MemberLogResponse;
import com.example.trello.domain.cardlog.entity.CardLog;
import com.example.trello.domain.cardlog.repository.CardLogRepository;
import com.example.trello.domain.list.repository.ListRepository;
import com.example.trello.domain.member.repository.MemberRepository;
import com.example.trello.domain.user.dto.AuthUser;
import com.example.trello.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.trello.common.response.ApiResponseBoardEnum.BOARD_NOT_FOUND;
import static com.example.trello.common.response.ApiResponseCardEnum.CARD_NOT_FOUND;
import static com.example.trello.common.response.ApiResponseListEnum.LIST_NOT_FOUND;
import static com.example.trello.common.response.ApiResponseWorkspaceEnum.WORKSPACE_ACCESS_DENIED;
import static com.example.trello.common.response.ApiResponseWorkspaceEnum.WORKSPACE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CardLogService {
    private final CardLogRepository cardLogRepository;
    private final WorkspaceRepository workspaceRepository;
    private final BoardRepository boardRepository;
    private final ListRepository listRepository;
    private final CardRepository cardRepository;
    private final MemberRepository memberRepository;

    /* 카드 담당자 변경 내역 */
    public ApiResponse<MemberLogResponse> getMemberLog(AuthUser authUser, Long workspaceId, Long boardsId, Long listId, Long cardId) {
        Card card = check_All_In_One(authUser.getId(), workspaceId, boardsId, listId, cardId);

        List<CardLog> cardMemberLogs = cardLogRepository.findByCardId(card.getId());
        List<CardMemberLogInfo> cardMemberLogInfo = cardMemberLogs.stream().map(CardMemberLogInfo::new).toList();

        ApiResponseEnum apiResponseEnum = ApiResponseCardMemberEnum.CARD_MEMBER_SAVE_OK;
        ApiResponse<MemberLogResponse> apiResponse = new ApiResponse<>(apiResponseEnum, new MemberLogResponse(cardMemberLogInfo));
        return apiResponse;
    }

    /* 워크스페이스, 보더, 리스트, 카드가 존재하는지 확인 */
    private Card check_All_In_One(Long userId, Long workspaceId, Long boardsId, Long listId, Long cardId) {
        boolean isUser = memberRepository.existsByUserIdAndWorkspaceId(userId, workspaceId);
        boolean isWorkspace = workspaceRepository.existsById(workspaceId);
        boolean isBoard = boardRepository.existsById(boardsId);
        boolean isList = listRepository.existsById(listId);
        if (!isUser) {
            throw new MemberException(WORKSPACE_ACCESS_DENIED);
        }
        if (!isWorkspace) {
            throw new WorkspaceException(WORKSPACE_NOT_FOUND);
        }
        if (!isBoard) {
            throw new BoardException(BOARD_NOT_FOUND);
        }
        if (!isList) {
            throw new BoardListException(LIST_NOT_FOUND);
        }
        return cardRepository.findById(cardId).orElseThrow(
                () -> new CardException(CARD_NOT_FOUND));
    }
}
