package com.example.community_service.community.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestDeleteManagerDto {

    private String targetUuid;

    public static RequestDeleteManagerDto formRequestDto(String targetUuid) {
        return RequestDeleteManagerDto.builder()
                .targetUuid(targetUuid)
                .build();
    }
}
