package com.example.community_service.community.dto.response;

import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QJoinedCommunityDto {

    private Long communityId;
    private String ownerUuid;
    private String profileImage;
    private String communityName;
    private String description;
    private String youtubeName;
    private Integer communitySize;
}
