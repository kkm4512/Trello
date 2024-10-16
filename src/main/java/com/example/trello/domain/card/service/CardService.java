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
import com.example.trello.domain.cardmember.dto.MemberInfo;
import com.example.trello.domain.cardmember.entity.CardMember;
import com.example.trello.domain.cardmember.repository.CardMemberRepository;
import com.example.trello.domain.comment.dto.CardCommentInfo;
import com.example.trello.domain.comment.entity.Comment;
import com.example.trello.domain.comment.repository.CommentRepository;
import com.example.trello.domain.list.entity.BoardList;
import com.example.trello.domain.list.repository.ListRepository;
import com.example.trello.domain.member.entity.Member;
import com.example.trello.domain.member.enums.MemberRole;
import com.example.trello.domain.member.repository.MemberRepository;
import com.example.trello.domain.user.dto.AuthUser;
import com.example.trello.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {
    private final MemberRepository memberRepository;
    private final CardMemberRepository cardMemberRepository;
    private final WorkspaceRepository workspaceRepository;
    private final BoardRepository boardRepository;
    private final ListRepository listRepository;
    private final CardRepository cardRepository;
    private final CommentRepository commentRepository;

    /* 카드 등록 */
    @Transactional
    public ApiResponse<SaveCardResponse> saveCard(AuthUser authUser, Long workspaceId, Long boardsId, Long listId, SaveCardRequest request) {
        // 워크 스페이스, 보더 존재하는지 확인
        validateWorkspaceAndBoard(workspaceId, boardsId);

        // 리스트 가져오기
        BoardList list = listOrElseThrow(listId);

        // 로그인한 유저가 워크스페이스 멤버 등록 유저인지 확인
        Member user = memberOrElseThrow(authUser);

        // 읽기 권한인지 확인
        userMemberRole(user);

        // 카드 등록
        Card card = cardRepository.save(new Card(request, list, user));

        ApiResponseEnum apiResponseEnum = ApiResponseCardEnum.CARD_SAVE_OK;
        ApiResponse<SaveCardResponse> apiResponse = new ApiResponse<>(apiResponseEnum, new SaveCardResponse(card));
        return apiResponse;
    }

    /* 카드 수정 */
    @CardChangeSlack
    @Transactional
    public ApiResponse<PutCardResponse> updateCard(AuthUser authUser, Long workspaceId, Long boardsId, Long listId, Long cardId, PutCardRequest request) {
        // 로그인한 유저가 워크스페이스 멤버 등록 유저인지 확인
        Member user = memberOrElseThrow(authUser);

        // 읽기 권한인지 확인
        userMemberRole(user);

        // 워크스페이스, 보더, 리스트, 카드 담당자 유무 올인원
        check_All_In_One(workspaceId, boardsId, listId, cardId, user);

        // 카드 가져오기
        Card card = cardOrElseThrow(cardId);

        card.update(request);

        ApiResponseEnum apiResponseEnum = ApiResponseCardEnum.CARD_UPDATE_OK;
        ApiResponse<PutCardResponse> apiResponse = new ApiResponse<>(apiResponseEnum, new PutCardResponse(card));
        return apiResponse;
    }

    /* 카드 상세 조회 */
    public ApiResponse<GetCardResponse> getCard(AuthUser authUser, Long workspaceId, Long boardsId, Long listId, Long cardId) {
        // 로그인한 유저가 워크스페이스 멤버 등록 유저인지 확인
        Member user = memberOrElseThrow(authUser);

        // 워크스페이스, 보더, 리스트 유무 확인
        validateWorkspaceAndBoardAndList(workspaceId, boardsId, listId);

        // 카드 가져오기
        Card card = cardOrElseThrow(cardId);

        List<CardMember> members = cardMemberRepository.findByCardId(card.getId());
        List<MemberInfo> memberInfos = members.stream().map(MemberInfo::new).toList();

        List<Comment> comments = commentRepository.findByCardId(card.getId());
        List<CardCommentInfo> commentInfos = comments.stream().map(CardCommentInfo::new).toList();

        ApiResponseEnum apiResponseEnum = ApiResponseCardEnum.CARD_GET_OK;
        ApiResponse<GetCardResponse> apiResponse = new ApiResponse<>(apiResponseEnum, new GetCardResponse(card, memberInfos, commentInfos));
        return apiResponse;
    }

    /* 카드 삭제 */
    @Transactional
    public ApiResponse<deleteResponse> deleteCard(AuthUser authUser, Long workspaceId, Long boardsId, Long listId, Long cardId) {
        // 보더에 등록된 멤버인지 확인
        Member user = memberRepository.findByUserId(authUser.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 유저는 멤버가 아닙니다."));

        // 읽기 권한인지 확인
        userMemberRole(user);

        // 워크스페이스, 보더, 리스트, 카드 담당자 유무 올인원
        check_All_In_One(workspaceId, boardsId, listId, cardId, user);

        // 카드 가져오기
        Card card = cardOrElseThrow(cardId);

        cardRepository.delete(card);

        ApiResponseEnum apiResponseEnum = ApiResponseCardEnum.CARD_DELETE_OK;
        ApiResponse<deleteResponse> apiResponse = new ApiResponse<>(apiResponseEnum, new deleteResponse(card));
        return apiResponse;
    }

    /* 카드 검색 */
    public ApiResponse<Page<SearchCardResponse>> searchCard(Long workspaceId, Long boardsId, SearchCardRequest request, int page, int size) {
        validateWorkspaceAndBoard(workspaceId, boardsId);

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<SearchCardResponse> cards = cardRepository.searchCards(request, pageable);

        ApiResponseEnum apiResponseEnum = ApiResponseCardEnum.CARD_SEARCH_OK;
        return new ApiResponse<>(apiResponseEnum, cards);
    }

    /* 유저 읽기 권한 확인 */
    private void userMemberRole(Member user) {
        if (user.getMemberRole() == MemberRole.READ_ONLY) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
    }

    /* 로그인한 유저 정보 */
    private Member memberOrElseThrow(AuthUser authUser) {
        return memberRepository.findByUserId(authUser.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 유저는 멤버가 아닙니다."));
    }

    /* 리스트 확인 */
    private BoardList listOrElseThrow(Long listId) {
        return listRepository.findById(listId).orElseThrow(
                () -> new IllegalArgumentException("해당 리스트가 없습니다."));
    }

    /* 카드 가져오기 */
    private Card cardOrElseThrow(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(
                () -> new IllegalArgumentException("해당 카드가 없습니다."));
    }

    /* 워크스페이스, 보더 유무 확인 */
    private void validateWorkspaceAndBoard(Long workspaceId, Long boardsId) {
        // 워크 스페이스, 보더, 리스트 존재하는지 확인
        boolean isWorkspace = workspaceRepository.existsById(workspaceId);
        boolean isBoard = boardRepository.existsById(boardsId);
        if (!isWorkspace) {
            throw new IllegalArgumentException("해당 워크 스페이스가 없습니다.");
        }
        if (!isBoard) {
            throw new IllegalArgumentException("해당 보더가 없습니다.");
        }
    }

    /* 워크 스페이스, 보더, 리스트 유무 확인 */
    private void validateWorkspaceAndBoardAndList(Long workspaceId, Long boardsId, Long listId) {
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
    }

    /* 워크스페이스, 보더, 리스트, 카드 담당자 유무 올인원 */
    private void check_All_In_One(Long workspaceId, Long boardsId, Long listId, Long cardId, Member user) {
        boolean isWorkspace = workspaceRepository.existsById(workspaceId);
        boolean isBoard = boardRepository.existsById(boardsId);
        boolean list = listRepository.existsById(listId);
        boolean cardMember = cardMemberRepository.existsByUserIdAndCardId(user.getUser().getId(), cardId);
        if (!isWorkspace) {
            throw new IllegalArgumentException("해당 워크 스페이스가 없습니다.");
        }
        if (!isBoard) {
            throw new IllegalArgumentException("해당 보더가 없습니다.");
        }
        if (!list) {
            throw new IllegalArgumentException("해당 리스트가 없습니다.");
        }
        if (!cardMember) {
            throw new IllegalArgumentException("해당 카드 담당자가 아닙니다.");
        }
    }
}
