package com.example.trello.domain.list.service;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.common.response.ApiResponseEnum;
import com.example.trello.common.response.ApiResponseTestEnum;
import com.example.trello.domain.list.dto.ListRequestDto;
import com.example.trello.domain.list.dto.ListResponseDto;
import com.example.trello.domain.list.entity.BoardList;
import com.example.trello.domain.list.entity.List;
import com.example.trello.domain.list.repository.ListRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ListService {

    private final ListRepository listRepository;
    private final BoardRepository boardRepository;

    // Board-List 생성
    public ApiResponse<ListResponseDto> createList(ListRequestDto requestDto) {
        Board board = boardRepository.findById(requestDto.getBoardId());

        // board에서 user 가져와서 역할 확인 -> 읽기 전용 멤버이면 에러 발생

        BoardList list = BoardList.builder()
                .board(board)
                .title(requestDto.getTitle())
                .build();

        list.saveListOrder(requestDto.getTitleOrder(), requestDto.getTitle()); // title 순서 저장

        BoardList savedList = listRepository.save(board); // List DB에 저장
        ListResponseDto responseData = ListResponseDto.of(savedList); // DTO로 변환

        ApiResponseEnum apiResponseEnum = ApiResponseTestEnum.TEST_SUCCESS;
        ApiResponse<ListResponseDto> apiResponse = new ApiResponse<>(apiResponseEnum, responseData);
        return apiResponse;
    }






}
