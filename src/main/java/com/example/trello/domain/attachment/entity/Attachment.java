package com.example.trello.domain.attachment.entity;

import com.example.trello.common.entity.Timestamped;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Attachment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String path;

//    @ManyToOne
//    @JoinColumn("card_id")
//    private final Card card;

//    @ManyToOne
//    @JoinColumn("user_id")
//    private final User user;


}
