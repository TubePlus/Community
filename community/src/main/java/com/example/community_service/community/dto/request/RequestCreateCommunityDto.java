package com.example.community_service.community.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestCreateCommunityDto {

    private String ownerUuid;
    private String communityName;
    private String description;
    private Boolean isCreator;
    private String bannerImage;
    private String profileImage;
    private String youtubeName;
}
