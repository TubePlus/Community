package com.example.community_service.community.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdateCommunityVo {

    private String bannerImage;
    private String profileImage;
    private String description;
    private String communityName;
}
