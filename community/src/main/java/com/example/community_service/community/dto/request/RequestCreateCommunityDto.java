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
    private String bannerImage;
    private String profileImage;
    private String youtubeName;

    public static RequestCreateCommunityDto formRequestDto(String ownerUuid, String communityName, String description,
           String bannerImage, String profileImage, String youtubeName) {

        return RequestCreateCommunityDto.builder()
                .ownerUuid(ownerUuid)
                .communityName(communityName)
                .description(description)
                .bannerImage(bannerImage)
                .profileImage(profileImage)
                .youtubeName(youtubeName)
                .build();
    }
}
