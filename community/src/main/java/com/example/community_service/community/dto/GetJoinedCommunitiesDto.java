package com.example.community_service.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class GetJoinedCommunitiesDto {

    @Builder
    @Getter
    public static class Request {
        private String userUuid;

        public static Request formRequestDto(String userUuid) {
            return Request.builder()
                    .userUuid(userUuid)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Response {

        private Long communityId;
        private String ownerUuid;
        private String profileImage;
        private String communityName;
        private String description;
        private String youtubeName;
        private Integer communityMemberCount;
    }
}
