package com.example.community_service.community.vo.response;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseJoinCommunityVo {

    private String userUuid;
    private Long communityId;

    public static ResponseJoinCommunityVo formResponseVo(String userUuid, Long communityId) {
        return ResponseJoinCommunityVo.builder()
                .userUuid(userUuid)
                .communityId(communityId)
                .build();
    }
}
