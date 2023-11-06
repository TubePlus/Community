package com.example.community_service.community.vo.response;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseUpdateCommunityVo {

    private Long communityId;

    public static ResponseUpdateCommunityVo formResponseVo(Long communityId) {
        return ResponseUpdateCommunityVo.builder()
                .communityId(communityId)
                .build();
    }
}
