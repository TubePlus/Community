package com.example.community_service.community.dto;

import lombok.*;

import java.util.List;

public class GetCommunitiesMatchingUuidsDto {

    @Builder
    @Getter
    public static class Request {

        private List<String> uuidList;

        public static Request formRequestDto(List<String> uuidList) {

            return Request.builder()
                    .uuidList(uuidList)
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
        private String youtubeName;
        private Integer communityMemberCount;
    }
}
