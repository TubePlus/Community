package com.example.community_service.community.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUpdateBanEndDateDto {

    private Long communityId;
    private String bannedUserUuid;
    private LocalDate banEndDate;
}
