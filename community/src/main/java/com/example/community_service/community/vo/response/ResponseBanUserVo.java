package com.example.community_service.community.vo.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseBanUserVo {

    private Long communityId;
    private String bannedUserUuid;
    private LocalDate banEndDate;
}
