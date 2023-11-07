package com.example.community_service.community.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUpdateCommunityDto {

    private Long communityId;

    public static ResponseUpdateCommunityDto formResponseDto(Long communityId) {
        return ResponseUpdateCommunityDto.builder()
                .communityId(communityId)
                .build();
    }
}
