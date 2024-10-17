package com.example.trello.domain.card.repository;

import com.example.trello.domain.card.dto.request.SearchCardRequest;
import com.example.trello.domain.card.dto.response.SearchCardResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CardQueryRepository {

    Page<SearchCardResponse> searchCards(SearchCardRequest request, Pageable pageable);
}