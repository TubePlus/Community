package com.example.community_service.community.vo.response;

import com.example.community_service.community.dto.GetJoinedCommunitiesDto;
import lombok.*;


import java.util.List;

@Getter
@Builder
public class ResponseGetJoinedCommunityListVo {

    private List<GetJoinedCommunitiesDto> communityList;
    private Long pageCount;

    public static ResponseGetJoinedCommunityListVo formResponseVo(List<GetJoinedCommunitiesDto> communityList, Long pageCount) {
        return ResponseGetJoinedCommunityListVo.builder()
                .communityList(communityList)
                .pageCount(pageCount)
                .build();
    }
}
