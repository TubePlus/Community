package com.example.community_service.community.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestJoinCommunityDto {

    private String userUuid;

    public static RequestJoinCommunityDto formRequestDto(String userUuid) {

        return RequestJoinCommunityDto.builder()
                .userUuid(userUuid)
                .build();
    }
}