package com.example.community_service.community.vo.response;

import com.example.community_service.community.dto.response.QJoinedCommunityDto;
import lombok.*;


import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseGetJoinedCommunityListVo {

    private List<QJoinedCommunityDto> communityList;
    private Long pageCount;

    public static ResponseGetJoinedCommunityListVo formResponseVo(List<QJoinedCommunityDto> communityList, Long pageCount) {
        return ResponseGetJoinedCommunityListVo.builder()
                .communityList(communityList)
                .pageCount(pageCount)
                .build();
    }
}
