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

    public static ResponseBanUserVo formResponseVo(Long communityId, String bannedUserUuid, LocalDate banEndDate) {
        return ResponseBanUserVo.builder()
                .communityId(communityId)
                .bannedUserUuid(bannedUserUuid)
                .banEndDate(banEndDate)
                .build();
    }
}
