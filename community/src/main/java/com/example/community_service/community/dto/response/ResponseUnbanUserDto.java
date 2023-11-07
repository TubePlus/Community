package com.example.community_service.community.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUnbanUserDto {

    private String unbannedUuid;

    public static ResponseUnbanUserDto formResponseDto(String unbannedUuid) {

        return ResponseUnbanUserDto.builder()
                .unbannedUuid(unbannedUuid)
                .build();
    }
}
