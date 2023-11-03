package com.example.community_service.community.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdateBanEndDateVo {

    @NotNull
    private String targetUuid;

    @NotNull
    private String managerUuid;
    private LocalDate banEndDate; // yyyy-MM-dd
}
