package com.example.community_service.community.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RequestGetCommunityIdVo {

    @NotNull
    private String userUuid;
}
