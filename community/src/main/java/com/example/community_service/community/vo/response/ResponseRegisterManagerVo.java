package com.example.community_service.community.vo.response;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseRegisterManagerVo {

    private Long communityId;
    private String managerUuid;
}
