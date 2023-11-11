package com.example.community_service.community.application;

import com.example.community_service.community.dto.GetCommunitiesMatchingUuidsDto;
import com.example.community_service.community.dto.GetCommunityMemberListDto;
import com.example.community_service.community.dto.GetJoinedCommunitiesDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchService {

    // 내가 가입한 커뮤니티 전체 조회
    Page<GetJoinedCommunitiesDto.Response> getAllJoinedCommunities(
            GetJoinedCommunitiesDto.Request requestDto, Pageable pageable);

    // 커뮤니티의 모든 유저 조회
    Page<GetCommunityMemberListDto.Response> getAllCommunityMembers(
            Long communityId, Pageable pageable);

    List<GetCommunitiesMatchingUuidsDto.Response> getCommunitiesMatchingUuids(List<String> uuidList);
}