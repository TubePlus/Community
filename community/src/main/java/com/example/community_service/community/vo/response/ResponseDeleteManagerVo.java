package com.example.community_service.community.vo.response;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseDeleteManagerVo {

    private String managerUuid;
    private Long communityId;

    public static ResponseDeleteManagerVo formResponseVo(String managerUuid, Long communityId) {

        return ResponseDeleteManagerVo.builder()
                .managerUuid(managerUuid)
                .communityId(communityId)
                .build();
    }
}
