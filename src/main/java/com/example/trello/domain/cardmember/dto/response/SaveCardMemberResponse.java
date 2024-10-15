package com.example.trello.domain.cardmember.dto.response;

import com.example.trello.domain.cardmember.dto.MemberInfo;
import com.example.trello.domain.cardmember.entity.CardMember;
import lombok.Getter;

import java.util.List;

@Getter
public class SaveCardMemberResponse {
    private final List<MemberInfo> member;

    public SaveCardMemberResponse(List<MemberInfo> members) {
        this.member = members;
    }
}
