package com.example.community_service.community.vo.response;

import com.example.community_service.community.domain.CommunityMember;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseGetCommunityMemberListVo {

    private List<CommunityMember> communityMemberList;

    public static ResponseGetCommunityMemberListVo formResponseVo(List<CommunityMember> communityMemberList) {
        return ResponseGetCommunityMemberListVo.builder()
                .communityMemberList(communityMemberList)
                .build();
    }
}
