package com.example.community_service.community.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdateBanEndDateDto {

    private String targetUuid;
    private String managerUuid;
    private LocalDate banEndDate;
}
