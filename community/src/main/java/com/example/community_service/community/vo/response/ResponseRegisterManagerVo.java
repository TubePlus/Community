package com.example.community_service.community.vo.response;

import com.example.community_service.community.dto.response.ResponseRegisterManagerDto;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseRegisterManagerVo {

    private Long communityId;
    private String managerUuid;

    public static ResponseRegisterManagerVo formResponseVo(Long communityId, String managerUuid) {

        return ResponseRegisterManagerVo.builder()
                .communityId(communityId)
                .managerUuid(managerUuid)
                .build();
    }
}
