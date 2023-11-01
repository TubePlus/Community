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
public class RequestRegisterManagerVo {

    @NotNull
    private String creatorUuid;

    @NotNull
    private String targetUuid;
}
