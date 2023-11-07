package com.example.community_service.community.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDeleteManagerDto {

    private String managerUuid;
    private Long communityId;

    public static ResponseDeleteManagerDto formResponseDto(String managerUuid, Long communityId) {

        return ResponseDeleteManagerDto.builder()
                .managerUuid(managerUuid)
                .communityId(communityId)
                .build();
    }
}
