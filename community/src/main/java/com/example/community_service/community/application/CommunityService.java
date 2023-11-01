package com.example.community_service.community.application;

import com.example.community_service.community.dto.request.*;
import com.example.community_service.community.dto.response.*;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface CommunityService {

    ResponseCreateCommunityDto createCommunity(RequestCreateCommunityDto requestCreateCommunityDto) throws JsonProcessingException;
    ResponseUpdateCommunityDto updateCommunity(RequestUpdateCommunityDto requestUpdateCommunityDto, Long communityId);
    ResponseBanUserDto banUser(RequestBanUserDto requestBanUserDto, Long communityId);
    ResponseUnbanUserDto unbanUser(RequestUnbanUserDto requestUnbanUserDto, Long communityId);
    ResponseGetBannedUserListDto getBannedUserList(RequestGetBannedUserListDto requestDto, Long communityId);
    ResponseJoinCommunityDto joinCommunity(RequestJoinCommunityDto requestDto, Long communityId);
}
