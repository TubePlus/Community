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
}
