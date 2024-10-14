package com.example.trello.domain.list.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "board_list")
@NoArgsConstructor
@Getter
public class List {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(name = "list_order")
    private Integer order;
}
