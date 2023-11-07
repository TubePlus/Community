package com.example.community_service.community.vo.response;

import com.example.community_service.community.dto.response.ResponseUnbanUserDto;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseUnbanUserVo {

    private String unbannedUuid;

    public static ResponseUnbanUserVo formResponseVo(String unbannedUuid) {

        return ResponseUnbanUserVo.builder()
                .unbannedUuid(unbannedUuid)
                .build();
    }
}
