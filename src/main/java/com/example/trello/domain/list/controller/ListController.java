package com.example.trello.domain.list.controller;

import com.example.trello.domain.list.service.ListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lists")
public class ListController {

    private final ListService listService;






}
