package com.example.community_service.community.dto;

import lombok.Builder;
import lombok.Getter;

public class CreateCommunityDto {

    @Getter
    @Builder
    public static class Request {

        private String ownerUuid;
        private String communityName;
        private String description;
        private String bannerImage;
        private String profileImage;
        private String youtubeName;

        public static Request formRequestDto(
                String ownerUuid, String communityName, String description,
                String bannerImage, String profileImage, String youtubeName) {

            return Request.builder()
                    .ownerUuid(ownerUuid)
                    .communityName(communityName)
                    .description(description)
                    .bannerImage(bannerImage)
                    .profileImage(profileImage)
                    .youtubeName(youtubeName)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class Response {

        private Long communityId;

        public static Response formResponseDto(Long communityId) {
            return Response.builder()
                    .communityId(communityId)
                    .build();
        }
    }
}
