package com.example.community_service.community.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class GetManagerListDto {

    @Getter
    @NoArgsConstructor
    public static class Response {

        private String userUuid;
        private LocalDateTime createdDate;
        private LocalDateTime updatedDate;
        private Boolean isMembershipUser;
    }
}
