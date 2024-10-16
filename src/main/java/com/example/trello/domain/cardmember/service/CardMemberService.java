package com.example.trello.domain.cardmember.service;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.common.response.ApiResponseCardEnum;
import com.example.trello.common.response.ApiResponseEnum;
import com.example.trello.domain.board.repository.BoardRepository;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.card.repository.CardRepository;
import com.example.trello.domain.cardmember.dto.MemberInfo;
import com.example.trello.domain.cardmember.dto.request.DeleteCardMemberRequest;
import com.example.trello.domain.cardmember.dto.request.SaveCardMemberRequest;
import com.example.trello.domain.cardmember.dto.response.DeleteCardMemberResponse;
import com.example.trello.domain.cardmember.dto.response.SaveCardMemberResponse;
import com.example.trello.domain.cardmember.entity.CardMember;
import com.example.trello.domain.cardmember.repository.CardMemberRepository;
import com.example.trello.domain.list.repository.ListRepository;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.repository.UserRepository;
import com.example.trello.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardMemberService {
    private final CardMemberRepository cardMemberRepository;
    private final WorkspaceRepository workspaceRepository;
    private final BoardRepository boardRepository;
    private final ListRepository listRepository;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    /* 카드 멤버 추가 */
    @Transactional
    public ApiResponse<SaveCardMemberResponse> saveCardMember(Long workspaceId, Long boardsId, Long listId, Long cardId, SaveCardMemberRequest request) {
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

        List<MemberInfo> members = new ArrayList<>();
        for (Long id : request.getMemberId()) {
            User member = userRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("해당 유저를 찾지 못했습니다."));
            CardMember cardMember = new CardMember(card, member);
            cardMemberRepository.save(cardMember);
            members.add(new MemberInfo(member.getId(), member.getEmail()));
        }

        ApiResponseEnum apiResponseEnum = ApiResponseCardEnum.CARD_SAVE_OK;
        ApiResponse<SaveCardMemberResponse> apiResponse = new ApiResponse<>(apiResponseEnum, new SaveCardMemberResponse(members));
        return apiResponse;
    }

    /* 카드 멤버 삭제 */
    @Transactional
    public ApiResponse<DeleteCardMemberResponse> deleteCardMember(Long workspaceId, Long boardsId, Long listId, Long cardId, DeleteCardMemberRequest request) {
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

        List<MemberInfo> members = new ArrayList<>();
        for (Long id : request.getMemberId()) {
            User member = userRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("해당 유저를 찾지 못했습니다."));
            CardMember cardMember = new CardMember(card, member);
            cardMemberRepository.delete(cardMember);
            members.add(new MemberInfo(member.getId(), member.getEmail()));
        }
        ApiResponseEnum apiResponseEnum = ApiResponseCardEnum.CARD_SAVE_OK;
        ApiResponse<DeleteCardMemberResponse> apiResponse = new ApiResponse<>(apiResponseEnum, new DeleteCardMemberResponse(members));
        return apiResponse;
    }
}
