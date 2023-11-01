package com.example.community_service.community.application;

import com.example.community_service.community.dto.request.*;
import com.example.community_service.community.dto.response.*;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface CommunityService {

    // 커뮤니티 관리
    ResponseCreateCommunityDto createCommunity(RequestCreateCommunityDto requestDto) throws JsonProcessingException;
    ResponseUpdateCommunityDto updateCommunity(RequestUpdateCommunityDto requestDto, Long communityId);
    ResponseGetCommunityMemberListDto getCommunityMemberList(RequestGetCommunityMemberListDto requestDto, Long communityId);

    // 커뮤니티 밴유저 관리
    ResponseBanUserDto banUser(RequestBanUserDto requestDto, Long communityId);
    ResponseUnbanUserDto unbanUser(RequestUnbanUserDto requestDto, Long communityId);
    ResponseGetBannedUserListDto getBannedUserList(RequestGetBannedUserListDto requestDto, Long communityId);

    // 커뮤니티 가입/탈퇴/조회
    ResponseJoinCommunityDto joinCommunity(RequestJoinCommunityDto requestDto, Long communityId);
    ResponseLeaveCommunityDto leaveCommunity(RequestLeaveCommunityDto requestDto, Long communityId);

    // 커뮤니티 매니저 관리
    ResponseRegisterManagerDto registerManager(RequestRegisterManagerDto requestDto, Long communityId);
}
