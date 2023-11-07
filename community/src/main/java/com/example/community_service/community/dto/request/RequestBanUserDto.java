package com.example.community_service.community.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestBanUserDto {

    private String targetUuid;
    private LocalDate banEndDate;

    public static RequestBanUserDto formRequestDto(String targetUuid, LocalDate banEndDate) {
        return RequestBanUserDto.builder()
                .targetUuid(targetUuid)
                .banEndDate(banEndDate)
                .build();
    }
}
