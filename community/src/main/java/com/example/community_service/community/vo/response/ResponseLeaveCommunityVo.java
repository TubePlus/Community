package com.example.community_service.community.vo.response;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseLeaveCommunityVo {

    private Long communityId;
    private String userUuid;

    public static ResponseLeaveCommunityVo formResponseVo(Long communityId, String userUuid) {
        return ResponseLeaveCommunityVo.builder()
                .communityId(communityId)
                .userUuid(userUuid)
                .build();
    }
}
