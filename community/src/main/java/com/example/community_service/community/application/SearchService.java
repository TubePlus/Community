package com.example.community_service.community.application;

import com.example.community_service.community.dto.*;
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

    // uuid 리스트에 해당하는 커뮤니티 리스트 조회
    List<GetCommunitiesMatchingUuidsDto.Response> getCommunitiesMatchingUuids(List<String> uuidList);

    // 해당 커뮤니티에서 밴 된 유저 모두 조회
    Page<GetBannedMemberListDto.Response> getAllBannedMembers(Long communityId, Pageable pageable);

    // 해당 커뮤니티의 매니저 모두 조회
    Page<GetManagerListDto.Response> getAllManagers(Long communityId, Pageable pageable);
}