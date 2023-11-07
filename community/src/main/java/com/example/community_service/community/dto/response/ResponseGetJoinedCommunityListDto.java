package com.example.community_service.community.dto.response;

import com.querydsl.core.Tuple;
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

    private List<QJoinedCommunityDto> communityList;
    private Long pageCount;

    public static ResponseGetJoinedCommunityListDto formResponseDto(
            List<QJoinedCommunityDto> communityList, Long pageCount) {

            return ResponseGetJoinedCommunityListDto.builder()
                .communityList(communityList)
                .pageCount(pageCount)
                .build();
    }
}