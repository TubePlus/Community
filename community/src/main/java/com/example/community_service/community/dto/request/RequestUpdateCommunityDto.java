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

    private String bannerImage;
    private String profileImage;
    private String description;
    private String communityName;

    public static RequestUpdateCommunityDto formRequestDto(
            String bannerImage, String profileImage, String description, String communityName) {

            return RequestUpdateCommunityDto.builder()
                .bannerImage(bannerImage)
                .profileImage(profileImage)
                .description(description)
                .communityName(communityName)
                .build(); }
}
