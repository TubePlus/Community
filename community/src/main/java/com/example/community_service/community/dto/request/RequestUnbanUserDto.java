package com.example.community_service.community.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestUnbanUserDto {

    private String targetUuid;

    public static RequestUnbanUserDto formRequestDto(String targetUuid) {

        return RequestUnbanUserDto.builder()
                .targetUuid(targetUuid)
                .build();
    }
}
