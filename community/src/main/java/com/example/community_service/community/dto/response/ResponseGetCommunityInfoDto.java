package com.example.community_service.community.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseGetCommunityInfoDto {

    private Long communityId;
    private String ownerUuid;
    private String bannerImage;
    private String profileImage;
    private String youtubeName;
    private String communityName;
    private String description;
    private Integer communityMemberCount;
    private LocalDate createdDate;
    private LocalDate updatedDate;
}
