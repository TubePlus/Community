package com.example.community_service.config.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommunityInteractionDto {
    private Long communityId;
    private Long point;
    private InteractionType interactionType;
}
