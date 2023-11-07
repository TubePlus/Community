package com.example.community_service.community.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestRegisterManagerDto {

    private String targetUuid;

    public static RequestRegisterManagerDto formRequestDto(String targetUuid) {

        return RequestRegisterManagerDto.builder()
                .targetUuid(targetUuid)
                .build();
    }
}