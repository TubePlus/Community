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
public class RequestCreateCommunityVo {

    @NotNull
    private String ownerUuid;

    @NotNull
    private String communityName;

    @NotNull
    private String token;

    @NotNull
    private Boolean isCreator;

    private String description;
}
