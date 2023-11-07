package com.example.community_service.side.dto;

import com.example.community_service.side.domain.SideType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommunitySideDto {
    private Integer sideOrder;
    private SideType sideType;
    private String title;
    private Long communityId;
}
