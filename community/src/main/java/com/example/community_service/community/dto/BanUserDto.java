package com.example.community_service.community.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

public class BanUserDto {

    @Getter
    @Builder
    public static class Request {

        private String targetUuid;
        private LocalDate banEndDate;

        public static Request formRequestDto(String targetUuid, LocalDate banEndDate) {
            return Request.builder()
                    .targetUuid(targetUuid)
                    .banEndDate(banEndDate)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class Response {

        private Long communityId;
        private String bannedUserUuid;
        private LocalDate banEndDate;

        public static Response formResponseDto(Long communityId, String bannedUserUuid, LocalDate banEndDate) {
            return Response.builder()
                    .communityId(communityId)
                    .bannedUserUuid(bannedUserUuid)
                    .banEndDate(banEndDate)
                    .build();
        }
    }
}
