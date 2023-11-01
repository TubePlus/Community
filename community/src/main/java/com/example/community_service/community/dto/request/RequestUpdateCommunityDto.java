package com.example.community_service.community.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestUpdateCommunityDto {

    private String ownerUuid;
    private String bannerImage;
    private String profileImage;
    private String description;
    private String communityName;
}
