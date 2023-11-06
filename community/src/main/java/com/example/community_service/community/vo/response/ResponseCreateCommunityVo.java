package com.example.community_service.community.vo.response;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseCreateCommunityVo {

    private Long communityId;

    public static ResponseCreateCommunityVo formResponseVo(Long communityId) {
        return ResponseCreateCommunityVo.builder()
                .communityId(communityId)
                .build();
    }
}
