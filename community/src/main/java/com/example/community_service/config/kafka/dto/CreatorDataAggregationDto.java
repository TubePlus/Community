package com.example.community_service.config.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreatorDataAggregationDto {
    private Long communityId;
    private String userUuid;
    private String communityName;
    private String youtubeName;
}
