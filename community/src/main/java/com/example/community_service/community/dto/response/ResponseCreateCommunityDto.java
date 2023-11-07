package com.example.community_service.community.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCreateCommunityDto {

    private Long communityId;

    public static ResponseCreateCommunityDto formResponseDto(Long communityId) {
        return ResponseCreateCommunityDto.builder()
                .communityId(communityId)
                .build();
    }
}
