package com.example.community_service.community.vo.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseGetCommunityIdVo {

    private Long communityId;

    public static ResponseGetCommunityIdVo formResponseVo(Long communityId) {

        return ResponseGetCommunityIdVo.builder()
                .communityId(communityId)
                .build();
    }
}
