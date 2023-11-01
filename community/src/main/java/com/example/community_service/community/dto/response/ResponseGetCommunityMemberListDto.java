package com.example.community_service.community.dto.response;

import com.example.community_service.community.domain.CommunityMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseGetCommunityMemberListDto {

    private List<CommunityMember> communityMemberList;
}
