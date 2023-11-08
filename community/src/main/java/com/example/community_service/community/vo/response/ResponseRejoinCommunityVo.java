package com.example.community_service.community.vo.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseRejoinCommunityVo {

    private String userUuid;
    private Long communityId;

    public static ResponseRejoinCommunityVo formResponseVo(String userUuid, Long communityId) {

        return ResponseRejoinCommunityVo.builder()
                .userUuid(userUuid)
                .communityId(communityId)
                .build();
    }
}
