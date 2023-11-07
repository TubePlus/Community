package com.example.community_service.community.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestLeaveCommunityDto {

    private String userUuid;

    public static RequestLeaveCommunityDto formRequestDto(String userUuid) {
        return RequestLeaveCommunityDto.builder()
                .userUuid(userUuid)
                .build();
    }
}
