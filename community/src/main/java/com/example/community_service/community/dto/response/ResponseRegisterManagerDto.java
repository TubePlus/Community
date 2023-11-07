package com.example.community_service.community.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseRegisterManagerDto {

    private Long communityId;
    private String managerUuid;

    public static ResponseRegisterManagerDto formResponseDto(Long communityId, String managerUuid) {

        return ResponseRegisterManagerDto.builder()
                .communityId(communityId)
                .managerUuid(managerUuid)
                .build();
    }
}
