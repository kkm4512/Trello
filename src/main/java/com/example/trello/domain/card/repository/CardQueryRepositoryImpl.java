package com.example.trello.domain.card.repository;

import com.example.trello.domain.card.dto.request.SearchCardRequest;
import com.example.trello.domain.card.dto.response.SearchCardResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


import static com.example.trello.domain.board.entity.QBoard.board;
import static com.example.trello.domain.card.entity.QCard.card;
import static com.example.trello.domain.list.entity.QBoardList.boardList;

@Repository
@RequiredArgsConstructor
public class CardQueryRepositoryImpl implements CardQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<SearchCardResponse> searchCards(SearchCardRequest request, Pageable pageable) {

        List<SearchCardResponse> result = queryFactory
                .select(Projections.fields(SearchCardResponse.class,
                        card.id.as("cardId"),
                        card.list.board.id.as("boardId"),
                        card.title,
                        card.content,
                        card.createdAt,
                        card.updatedAt
                ))
                .from(card)
                .leftJoin(card.list, boardList)
                .leftJoin(card.list.board, board)
                .where(
                        boardIdEq(request.getBoardId()), // 보드 ID로 검색(해당 보드의 카드만 대상)
                        cardIdEq(request.getCardId()), // 카드 ID로 검색
                        cardTitleContains(request.getTitle()), // 카드 제목으로 검색
                        cardContentContains(request.getContent()), // 카드 내용으로 검색
                        cardCreatedAtBetween(request.getStartAt(), request.getEndAt()) // 카드 생성 기간으로 검색
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .groupBy(card.id)
                .orderBy(card.id.desc())
                .fetch();

        Long totalCount = queryFactory
                .select(Wildcard.count)
                .from(card)
                .where(
                        boardIdEq(request.getBoardId()),
                        cardIdEq(request.getCardId()),
                        cardTitleContains(request.getTitle()),
                        cardContentContains(request.getContent()),
                        cardCreatedAtBetween(request.getStartAt(), request.getEndAt())
                )
                .fetchOne();

        return new PageImpl<>(result, pageable, totalCount);
    }

    // 보드 번호 검색(보드 번호 있으면 해당 보드 안에서 카드 검색 or 없으면 전체 카드 검색)
    private BooleanExpression boardIdEq(Long boardId) {
        return boardId != null ? card.list.board.id.eq(boardId) : null;
    }

    // 카드 ID 검색
    private BooleanExpression cardIdEq(Long cardId) {
        return cardId != null ? card.id.eq(cardId) : null;
    }

    // 카드 제목 검색
    private BooleanExpression cardTitleContains(String title) {
        return title != null ? card.title.containsIgnoreCase(title) : null;
    }

    // 카드 내용 검색
    private BooleanExpression cardContentContains(String content) {
        return content != null ? card.content.containsIgnoreCase(content) : null;
    }

    // 카드 작성자 검색
    private BooleanExpression cardUserEq(Long userId) {
        return userId != null ? card.user.id.eq(userId) : null;
    }

    // 생성 기간으로 검색
    private BooleanExpression cardCreatedAtBetween(LocalDateTime startAt, LocalDateTime endAt) {
        if (startAt != null && endAt != null) {
            return card.createdAt.between(startAt, endAt);
        } else if (startAt != null) {
            return card.createdAt.after(startAt);
        } else if (endAt != null) {
            return card.createdAt.before(endAt);
        } else {
            return null;
        }
    }

}