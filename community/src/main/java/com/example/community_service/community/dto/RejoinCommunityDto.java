package com.example.community_service.community.dto;

import lombok.Builder;
import lombok.Getter;

public class RejoinCommunityDto {

    @Getter
    @Builder
    public static class Request {

        private String userUuid;

        public static Request formRequestDto(String userUuid) {

            return Request.builder()
                    .userUuid(userUuid)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class Response {

        private String userUuid;
        private Long communityId;

        public static Response formResponseDto(String userUuid, Long communityId) {

            return Response.builder()
                    .userUuid(userUuid)
                    .communityId(communityId)
                    .build();
        }
    }
}
