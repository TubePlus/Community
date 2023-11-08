package com.example.community_service.community.dto;

import lombok.Builder;
import lombok.Getter;

public class RejoinCommunityDto {

    @Getter
    @Builder
    public static class RequestRejoinCommunityDto {

        private String userUuid;

        public static RequestRejoinCommunityDto formRequestDto(String userUuid) {

            return RequestRejoinCommunityDto.builder()
                    .userUuid(userUuid)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class ResponseRejoinCommunityDto {

        private String userUuid;
        private Long communityId;

        public static ResponseRejoinCommunityDto formResponseDto(String userUuid, Long communityId) {

            return ResponseRejoinCommunityDto.builder()
                    .userUuid(userUuid)
                    .communityId(communityId)
                    .build();
        }
    }
}
