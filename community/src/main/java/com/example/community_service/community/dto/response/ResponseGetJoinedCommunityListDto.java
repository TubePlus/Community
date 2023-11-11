package com.example.community_service.community.dto.response;

import com.example.community_service.community.dto.GetJoinedCommunitiesDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseGetJoinedCommunityListDto {

    private List<GetJoinedCommunitiesDto> communityList;
    private Long totalPageCount;

    public static ResponseGetJoinedCommunityListDto formResponseDto(
            List<GetJoinedCommunitiesDto> communityList, Long totalPageCount) {

            return ResponseGetJoinedCommunityListDto.builder()
                .communityList(communityList)
                .totalPageCount(totalPageCount)
                .build();
    }
}