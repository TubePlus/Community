package com.example.community_service.community.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUpdateBanEndDateVo {

    private String bannedUserUuid;
    private LocalDate banEndDate;
    private Long communityId;
}
