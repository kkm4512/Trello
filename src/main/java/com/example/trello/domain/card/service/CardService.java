package com.example.trello.domain.card.service;

import com.example.trello.common.annotation.CardChangeSlack;
import com.example.trello.common.exception.*;
import com.example.trello.common.response.ApiResponse;
import com.example.trello.common.response.ApiResponseCardEnum;
import com.example.trello.common.response.ApiResponseEnum;
import com.example.trello.domain.board.repository.BoardRepository;
import com.example.trello.domain.card.dto.request.PutCardRequest;
import com.example.trello.domain.card.dto.request.SaveCardRequest;
import com.example.trello.domain.card.dto.request.SearchCardRequest;
import com.example.trello.domain.card.dto.response.*;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.example.trello.common.response.ApiResponseBoardEnum.BOARD_NOT_FOUND;
import static com.example.trello.common.response.ApiResponseCardEnum.CARD_NOT_FOUND;
import static com.example.trello.common.response.ApiResponseCardEnum.NOT_CARD_OWNER;
import static com.example.trello.common.response.ApiResponseListEnum.LIST_NOT_FOUND;
import static com.example.trello.common.response.ApiResponseMemberEnum.MEMBER_NOT_FOUND;
import static com.example.trello.common.response.ApiResponseMemberEnum.WORKSPACE_ADMIN_REQUIRED;
import static com.example.trello.common.response.ApiResponseWorkspaceEnum.WORKSPACE_ACCESS_DENIED;
import static com.example.trello.common.response.ApiResponseWorkspaceEnum.WORKSPACE_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardService {
    private final MemberRepository memberRepository;
    private final CardMemberRepository cardMemberRepository;
    private final WorkspaceRepository workspaceRepository;
    private final BoardRepository boardRepository;
    private final ListRepository listRepository;
    private final CardRepository cardRepository;
    private final CommentRepository commentRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String CARD_VIEW_KEY_PREFIX = "card:view:"; // 특정 카드에 대한 전체 조회 수
    private static final String USER_VIEW_KEY_PREFIX = "card:user:view:"; // 특정 카드에 대해 특정 유저가 이미 조회하였는지에 대한 여부

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

    /* 카드 상세 조회(MySQL 적용) */
//    public ApiResponse<GetCardResponse> getCard(AuthUser authUser, Long workspaceId, Long boardsId, Long listId, Long cardId) {
//        // 로그인한 유저가 워크스페이스 멤버 등록 유저인지 확인
//        Member user = memberOrElseThrow(authUser);
//
//        // 워크스페이스, 보더, 리스트 유무 확인
//        validateWorkspaceAndBoardAndList(workspaceId, boardsId, listId);
//
//        // 카드 가져오기
//        Card card = cardOrElseThrow(cardId);
//
//        List<CardMember> members = cardMemberRepository.findByCardId(card.getId());
//        List<MemberInfo> memberInfos = members.stream().map(MemberInfo::new).toList();
//
//        List<Comment> comments = commentRepository.findByCardId(card.getId());
//        List<CardCommentInfo> commentInfos = comments.stream().map(CardCommentInfo::new).toList();
//
//        ApiResponseEnum apiResponseEnum = ApiResponseCardEnum.CARD_GET_OK;
//        ApiResponse<GetCardResponse> apiResponse = new ApiResponse<>(apiResponseEnum, new GetCardResponse(card, memberInfos, commentInfos));
//        return apiResponse;
//    }

    /* 카드 상세 조회(Redis 적용) */
    public ApiResponse<GetCardResponse> getCard(AuthUser authUser, Long workspaceId, Long boardsId, Long listId, Long cardId) {
        // 로그인한 유저가 워크스페이스 멤버 등록 유저인지 확인 && 워크스페이스, 보더, 리스트 유무 확인
        validateWorkspaceAndBoardAndList(authUser.getId(), workspaceId, boardsId, listId);

        // 유저가 이미 이 카드를 조회했는지 체크(어뷰징 방지)
        String userViewKey = USER_VIEW_KEY_PREFIX + cardId + ":" + authUser.getId();

        if (redisTemplate.opsForValue().get(userViewKey) == null) { // 만약 현재 유저가 오늘 아직 이 카드를 조회하지 않았다면(cache miss) 조회수 증가
            String cardViewKey = CARD_VIEW_KEY_PREFIX + cardId;
            redisTemplate.opsForValue().increment(cardViewKey, 1); // 유저가 해당 카드를 처음 조회한 것이므로 조회수 증가 및 redis 저장

            // 유저가 조회한 정보를 24시간 동안 유지하여 동일 유저의 중복 조회 방지
            redisTemplate.opsForValue().set(userViewKey, true); // 해당 유저의 해당 카드 조회 여부 체크 및 redis 저장
            redisTemplate.expire(userViewKey, Duration.ofDays(1)); // 조회 표시 유효 기간 1일
        }

        // 카드 가져오기
        Card card = cardOrElseThrow(cardId);
        updateCardRanking(cardId); // 카드 조회 수 랭킹 업데이트

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
        Member user = memberOrElseThrow(authUser);

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

    /* 카드 랭킹 조회 */
    public ApiResponse<List<CardRankResponse>> getTopRankedCards() {
        String rankingKey = "card:ranking";
        Set<Object> rankedCards = redisTemplate.opsForZSet().reverseRange(rankingKey, 0, 9);// 상위 10개의 카드 조회

        List<CardRankResponse> cardRanking = new ArrayList<>(); // 등수, 카드 ID 담을 리스트
        int rank = 1; // 등수 시작값

        if (rankedCards != null) {
            for (Object cardId : rankedCards) {
                cardRanking.add(new CardRankResponse(rank++, Long.valueOf(cardId.toString()))); // 카드 ID와 등수 추가
            }
        }

        return new ApiResponse<>(ApiResponseCardEnum.CARD_GET_OK, cardRanking);
    }

    /* 카드 조회 랭킹 업데이트*/
    public void updateCardRanking(Long cardId) {
        String rankingKey = "card:ranking";
        redisTemplate.opsForZSet().incrementScore(rankingKey, cardId, 1);
    }

    /* 유저 읽기 권한 확인 */
    private void userMemberRole(Member user) {
        if (user.getMemberRole() == MemberRole.READ_ONLY) {
            throw new MemberException(WORKSPACE_ADMIN_REQUIRED);
        }
    }

    /* 로그인한 유저 정보 */
    private Member memberOrElseThrow(AuthUser authUser) {
        return memberRepository.findByUserId(authUser.getId()).orElseThrow(
                () -> new MemberException(MEMBER_NOT_FOUND));
    }

    /* 리스트 확인 */
    private BoardList listOrElseThrow(Long listId) {
        return listRepository.findById(listId).orElseThrow(
                () -> new BoardListException(LIST_NOT_FOUND));
    }

    /* 카드 가져오기 */
    private Card cardOrElseThrow(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(
                () -> new CardException(CARD_NOT_FOUND));
    }

    /* 워크스페이스, 보더 유무 확인 */
    private void validateWorkspaceAndBoard(Long workspaceId, Long boardsId) {
        // 워크 스페이스, 보더, 리스트 존재하는지 확인
        boolean isWorkspace = workspaceRepository.existsById(workspaceId);
        boolean isBoard = boardRepository.existsById(boardsId);
        if (!isWorkspace) {
            throw new WorkspaceException(WORKSPACE_NOT_FOUND);
        }
        if (!isBoard) {
            throw new BoardException(BOARD_NOT_FOUND);
        }
    }

    /* 워크 스페이스, 보더, 리스트 유무 확인 */
    private void validateWorkspaceAndBoardAndList(Long userId, Long workspaceId, Long boardsId, Long listId) {
        boolean isUser = memberRepository.existsByUserIdAndWorkspaceId(userId,workspaceId);
        boolean isWorkspace = workspaceRepository.existsById(workspaceId);
        boolean isBoard = boardRepository.existsById(boardsId);
        boolean list = listRepository.existsById(listId);
        if (!isUser) {
            throw new MemberException(WORKSPACE_ACCESS_DENIED);
        }
        if (!isWorkspace) {
            throw new WorkspaceException(WORKSPACE_NOT_FOUND);
        }
        if (!isBoard) {
            throw new BoardException(BOARD_NOT_FOUND);
        }
        if (!list) {
            throw new BoardListException(LIST_NOT_FOUND);
        }
    }

    /* 워크스페이스, 보더, 리스트, 카드 담당자 유무 올인원 */
    private void check_All_In_One(Long workspaceId, Long boardsId, Long listId, Long cardId, Member user) {
        boolean isWorkspace = workspaceRepository.existsById(workspaceId);
        boolean isBoard = boardRepository.existsById(boardsId);
        boolean list = listRepository.existsById(listId);
        boolean cardMember = cardMemberRepository.existsByUserIdAndCardId(user.getUser().getId(), cardId);
        if (!isWorkspace) {
            throw new WorkspaceException(WORKSPACE_NOT_FOUND);
        }
        if (!isBoard) {
            throw new BoardException(BOARD_NOT_FOUND);
        }
        if (!list) {
            throw new BoardListException(LIST_NOT_FOUND);
        }
        if (!cardMember) {
            throw new CardException(NOT_CARD_OWNER);
        }
    }


}



