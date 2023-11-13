package com.example.community_service.community.dto;

import lombok.Builder;
import lombok.Getter;

public class DeleteManagerDto {

    @Getter
    @Builder
    public static class Request {

        private String targetUuid;

        public static Request formRequestDto(String targetUuid) {
            return Request.builder()
                    .targetUuid(targetUuid)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class Response {

        private String managerUuid;
        private Long communityId;

        public static Response formResponseDto(String managerUuid, Long communityId) {
            return Response.builder()
                    .managerUuid(managerUuid)
                    .communityId(communityId)
                    .build();
        }
    }
}
