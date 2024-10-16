package com.example.trello.domain.cardmember.dto.response;

import com.example.trello.domain.cardmember.dto.MemberInfo;
import lombok.Getter;

import java.util.List;

@Getter
public class DeleteCardMemberResponse {
    private List<MemberInfo> member;

    public DeleteCardMemberResponse(List<MemberInfo> members) {
        this.member = members;
    }
}
