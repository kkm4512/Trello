package com.example.trello.domain.cardmember.service;

import com.example.trello.common.exception.*;
import com.example.trello.common.response.ApiResponse;
import com.example.trello.common.response.ApiResponseCardEnum;
import com.example.trello.common.response.ApiResponseCardMemberEnum;
import com.example.trello.common.response.ApiResponseEnum;
import com.example.trello.domain.board.repository.BoardRepository;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.card.repository.CardRepository;
import com.example.trello.domain.cardlog.entity.CardLog;
import com.example.trello.domain.cardlog.enums.MemberChangeStatus;
import com.example.trello.domain.cardlog.repository.CardLogRepository;
import com.example.trello.domain.cardmember.dto.MemberInfo;
import com.example.trello.domain.cardmember.dto.request.DeleteCardMemberRequest;
import com.example.trello.domain.cardmember.dto.request.SaveCardMemberRequest;
import com.example.trello.domain.cardmember.dto.response.DeleteCardMemberResponse;
import com.example.trello.domain.cardmember.dto.response.SaveCardMemberResponse;
import com.example.trello.domain.cardmember.entity.CardMember;
import com.example.trello.domain.cardmember.repository.CardMemberRepository;
import com.example.trello.domain.list.repository.ListRepository;
import com.example.trello.domain.member.entity.Member;
import com.example.trello.domain.member.enums.MemberRole;
import com.example.trello.domain.member.repository.MemberRepository;
import com.example.trello.domain.user.dto.AuthUser;
import com.example.trello.domain.user.repository.UserRepository;
import com.example.trello.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.trello.common.response.ApiResponseBoardEnum.BOARD_NOT_FOUND;
import static com.example.trello.common.response.ApiResponseCardEnum.CARD_NOT_FOUND;
import static com.example.trello.common.response.ApiResponseCardEnum.NOT_CARD_OWNER;
import static com.example.trello.common.response.ApiResponseListEnum.LIST_NOT_FOUND;
import static com.example.trello.common.response.ApiResponseMemberEnum.MEMBER_NOT_FOUND;
import static com.example.trello.common.response.ApiResponseMemberEnum.WORKSPACE_ADMIN_REQUIRED;
import static com.example.trello.common.response.ApiResponseWorkspaceEnum.WORKSPACE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CardMemberService {
    private final CardMemberRepository cardMemberRepository;
    private final MemberRepository memberRepository;
    private final WorkspaceRepository workspaceRepository;
    private final BoardRepository boardRepository;
    private final ListRepository listRepository;
    private final CardRepository cardRepository;
    private final CardLogRepository cardLogRepository;

    /* 카드 멤버 추가 */
    @Transactional
    public ApiResponse<SaveCardMemberResponse> saveCardMember(AuthUser authUser, Long workspaceId, Long boardsId, Long listId, Long cardId, SaveCardMemberRequest request) {
        // 로그인한 유저가 워크스페이스 멤버 등록 유저인지 확인
        Member user = memberOrElseThrow(authUser.getId());

        // 읽기 권한인지 확인
        userMemberRole(user);

        // 카드 담당자인지 확인
        checkCardMember(user, cardId);

        // 워크스페이스, 보더, 리스트, 카드가 존재하는지 확인
        Card card = check_All_In_One(workspaceId, boardsId, listId, cardId);

        // 등록하려는 멤버 각각 추출 후 담당자 등록
        List<MemberInfo> members = new ArrayList<>();
        for (Long id : request.getMemberId()) {
            Member member = memberRepository.findByUserId(id).orElseThrow(
                    () -> new IllegalArgumentException("해당 유저는 멤버가 아닙니다."));
            CardMember cardMember = new CardMember(card, member);

            // 카드 담당자 추가관련 로그데이터 저장
            CardLog cardLog = new CardLog(user, member, card, MemberChangeStatus.ADD);
            cardLogRepository.save(cardLog);

            cardMemberRepository.save(cardMember);
            members.add(new MemberInfo(cardMember));
        }

        ApiResponseEnum apiResponseEnum = ApiResponseCardEnum.CARD_SAVE_OK;
        ApiResponse<SaveCardMemberResponse> apiResponse = new ApiResponse<>(apiResponseEnum, new SaveCardMemberResponse(members));
        return apiResponse;
    }

    /* 카드 멤버 삭제 */
    @Transactional
    public ApiResponse<DeleteCardMemberResponse> deleteCardMember(AuthUser authUser, Long workspaceId, Long boardsId, Long listId, Long cardId, DeleteCardMemberRequest request) {
        // 로그인한 유저가 워크스페이스 멤버 등록 유저인지 확인
        Member user = memberOrElseThrow(authUser.getId());

        // 읽기 권한인지 확인
        userMemberRole(user);

        // 카드 담당자인지 확인
        checkCardMember(user, cardId);

        // 워크스페이스, 보더, 리스트, 카드가 존재하는지 확인
        Card card = check_All_In_One(workspaceId, boardsId, listId, cardId);

        // 등록하려는 멤버 각각 추출 후 담당자 삭제
        List<MemberInfo> members = new ArrayList<>();
        for (Long id : request.getMemberId()) {
            Member member = memberOrElseThrow(id);
            CardMember cardMember = new CardMember(card, member);

            // 카드 담당자 삭제관련 로그데이터 저장
            CardLog cardLog = new CardLog(user, member, card, MemberChangeStatus.DELETE);
            cardLogRepository.save(cardLog);

            cardMemberRepository.delete(cardMember);
            members.add(new MemberInfo(cardMember));
        }

        ApiResponseEnum apiResponseEnum = ApiResponseCardEnum.CARD_SAVE_OK;
        ApiResponse<DeleteCardMemberResponse> apiResponse = new ApiResponse<>(apiResponseEnum, new DeleteCardMemberResponse(members));
        return apiResponse;
    }

    /* 로그인한 유저 정보 */
    private Member memberOrElseThrow(Long userId) {
        return memberRepository.findByUserId(userId).orElseThrow(
                () -> new MemberException(MEMBER_NOT_FOUND));
    }

    /* 유저 읽기 권한 확인 */
    private void userMemberRole(Member user) {
        if (user.getMemberRole() == MemberRole.READ_ONLY) {
            throw new MemberException(WORKSPACE_ADMIN_REQUIRED);
        }
    }

    /* 카드 담당자 유무 */
    private void checkCardMember(Member user, Long cardId) {
        boolean authMember = cardMemberRepository.existsByUserIdAndCardId(user.getId(), cardId);
        if (!authMember) {
            throw new CardException(NOT_CARD_OWNER);
        }
    }

    /* 워크스페이스, 보더, 리스트, 카드가 존재하는지 확인 */
    private Card check_All_In_One(Long workspaceId, Long boardsId, Long listId, Long cardId) {
        boolean isWorkspace = workspaceRepository.existsById(workspaceId);
        boolean isBoard = boardRepository.existsById(boardsId);
        boolean list = listRepository.existsById(listId);
        if (!isWorkspace) {
            throw new WorkspaceException(WORKSPACE_NOT_FOUND);
        }
        if (!isBoard) {
            throw new BoardException(BOARD_NOT_FOUND);
        }
        if (!list) {
            throw new BoardListException(LIST_NOT_FOUND);
        }
        return cardRepository.findById(cardId).orElseThrow(
                () -> new CardException(CARD_NOT_FOUND));
    }
}
