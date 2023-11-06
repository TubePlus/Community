package com.example.community_service.community.vo.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseGetCommunityInfoVo {

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

    public static ResponseGetCommunityInfoVo formResponseVo(Long communityId, String ownerUuid, String bannerImage,
            String profileImage, String youtubeName, String communityName, String description,
            Integer communityMemberCount, LocalDate createdDate, LocalDate updatedDate) {

        return ResponseGetCommunityInfoVo.builder()
                .communityId(communityId)
                .ownerUuid(ownerUuid)
                .bannerImage(bannerImage)
                .profileImage(profileImage)
                .youtubeName(youtubeName)
                .communityName(communityName)
                .description(description)
                .communityMemberCount(communityMemberCount)
                .createdDate(createdDate)
                .updatedDate(updatedDate)
                .build();
    }
}
