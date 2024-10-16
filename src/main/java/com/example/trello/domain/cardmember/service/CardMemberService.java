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

@Service
@RequiredArgsConstructor
public class CardMemberService {
    private final CardMemberRepository cardMemberRepository;
    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final WorkspaceRepository workspaceRepository;
    private final BoardRepository boardRepository;
    private final ListRepository listRepository;
    private final CardRepository cardRepository;

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
            String email = memberRepository.findEmailByUserId(id).orElseThrow(
                    () -> new IllegalArgumentException("해당 유저의 이메일을 찾을 수 없습니다.")).toString();
            CardMember cardMember = new CardMember(card, member);
            cardMemberRepository.save(cardMember);
            members.add(new MemberInfo(member.getId(), email));
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
            String email = userRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("해당 유저의 이메일을 찾을 수 없습니다.")).getEmail();
            CardMember cardMember = new CardMember(card, member);
            cardMemberRepository.delete(cardMember);
            members.add(new MemberInfo(member.getId(), email));
        }

        ApiResponseEnum apiResponseEnum = ApiResponseCardEnum.CARD_SAVE_OK;
        ApiResponse<DeleteCardMemberResponse> apiResponse = new ApiResponse<>(apiResponseEnum, new DeleteCardMemberResponse(members));
        return apiResponse;
    }

    /* 로그인한 유저 정보 */
    private Member memberOrElseThrow(Long userId) {
        return memberRepository.findByUserId(userId).orElseThrow(
                () -> new IllegalArgumentException("해당 유저는 멤버가 아닙니다."));
    }

    /* 유저 읽기 권한 확인 */
    private void userMemberRole(Member user) {
        if (user.getMemberRole() == MemberRole.READ_ONLY) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
    }

    /* 카드 담당자 유무 */
    private void checkCardMember(Member user, Long cardId) {
        boolean authMember = cardMemberRepository.existsByUserIdAndCardId(user.getId(), cardId);
        if (!authMember) {
            throw new IllegalArgumentException("해당 카드의 담당자가 아닙니다.");
        }
    }

    /* 워크스페이스, 보더, 리스트, 카드가 존재하는지 확인 */
    private Card check_All_In_One(Long workspaceId, Long boardsId, Long listId, Long cardId) {
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
        return cardRepository.findById(cardId).orElseThrow(
                () -> new IllegalArgumentException("해당 카드가 없습니다."));
    }
}
