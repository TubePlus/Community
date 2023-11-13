package com.example.community_service.community.dto;

import lombok.Getter;

import java.time.LocalDateTime;

public class GetCommunityMemberListDto {

    @Getter
    public static class Response {

        private String userUuid;
        private Boolean isBanned;
        private Boolean isManager;
        private LocalDateTime createdDate;
        private LocalDateTime updatedDate;
    }
}
