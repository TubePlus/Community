package com.example.community_service.community.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class GetJoinedCommunitiesDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {

        private String userUuid;

//        public static Request formRequestDto(String userUuid) {
//            return Request.builder()
//                    .userUuid(userUuid)
//                    .build();
//        }
    }

    @Getter
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
