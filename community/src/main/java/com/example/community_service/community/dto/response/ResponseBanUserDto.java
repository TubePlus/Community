package com.example.community_service.community.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBanUserDto {

    private Long communityId;
    private String bannedUserUuid;
    private LocalDate banEndDate;

    public static ResponseBanUserDto formResponseDto(Long communityId, String bannedUserUuid, LocalDate banEndDate) {
        return ResponseBanUserDto.builder()
                .communityId(communityId)
                .bannedUserUuid(bannedUserUuid)
                .banEndDate(banEndDate)
                .build();
    }
}