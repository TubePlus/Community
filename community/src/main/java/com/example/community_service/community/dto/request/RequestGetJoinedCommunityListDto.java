package com.example.community_service.community.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestGetJoinedCommunityListDto {

    private String userUuid;

    public static RequestGetJoinedCommunityListDto formRequestDto(String userUuid) {

        return RequestGetJoinedCommunityListDto.builder()
                .userUuid(userUuid)
                .build();
    }
}
