package com.example.community_service.community.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class GetBannedMemberListDto {

    @Getter
    @NoArgsConstructor
    public static class Response {

        private String userUuid;
        private LocalDate createdDate;
        private LocalDate updatedDate;
        private LocalDate banEndDate;
        private Boolean isMembershipUser;
    }
}
