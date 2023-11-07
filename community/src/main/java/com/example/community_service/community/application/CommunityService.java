package com.example.community_service.community.application;

import com.example.community_service.community.domain.Community;
import com.example.community_service.community.dto.request.*;
import com.example.community_service.community.dto.response.*;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface CommunityService { // todo: 인터페이스 도메인별로 구분해서 나누기

    /**
     * 커뮤니티 생성/삭제 관련
     */

    // 커뮤니티 생성
    ResponseCreateCommunityDto createCommunity(RequestCreateCommunityDto requestDto);

    /**
     * 커뮤니티 조회 관련
     */

    // todo: 페이지네이션이라서 따로 빼야할수도 있음.
    // 가입한 커뮤니티 목록 조회
    ResponseGetJoinedCommunityListDto getJoinedCommunityList(
            RequestGetJoinedCommunityListDto requestDto, Integer count, Integer page);
    
    // 커뮤니티 정보 상세 조회
    ResponseGetCommunityInfoDto getCommunityInfo(Long communityId);

    // 커뮤니티 불러오기
    Community searchCommunity(Long communityId);

    /**
     * 커뮤니티 업데이트 관련
     */
    
    // 커뮤니티 회원수 업데이트
    void updateCommunityMemberCount(Long communityId, Integer memberCount);

    // 커뮤니티 정보 수정
    ResponseUpdateCommunityDto updateCommunity(RequestUpdateCommunityDto requestDto, Long communityId);










//    // 커뮤니티 관리
//    ResponseCreateCommunityDto createCommunity(RequestCreateCommunityDto requestDto) throws JsonProcessingException;
//    ResponseUpdateCommunityDto updateCommunity(RequestUpdateCommunityDto requestDto, Long communityId);
//
//    // 커뮤니티 밴유저 관리
//    ResponseBanUserDto banUser(RequestBanUserDto requestDto, Long communityId);
//    ResponseUpdateBanEndDateDto updateBanEndDate(RequestUpdateBanEndDateDto requestDto, Long communityId);
//    ResponseUnbanUserDto unbanUser(RequestUnbanUserDto requestDto, Long communityId);
//    ResponseGetBannedUserListDto getBannedUserList(RequestGetBannedUserListDto requestDto, Long communityId);
//
//    // 커뮤니티 가입/탈퇴/조회
//    ResponseJoinCommunityDto joinCommunity(RequestJoinCommunityDto requestDto, Long communityId);
//    ResponseLeaveCommunityDto leaveCommunity(RequestLeaveCommunityDto requestDto, Long communityId);
//    ResponseGetJoinedCommunityListDto getJoinedCommunityList(
//            RequestGetJoinedCommunityListDto requestDto, Integer count, Integer page);
//    ResponseGetCommunityInfoDto getCommunityInfo(Long communityId);
//
//    // 커뮤니티 매니저 관리
//    ResponseRegisterManagerDto registerManager(RequestRegisterManagerDto requestDto, Long communityId);
//    ResponseDeleteManagerDto deleteManager(RequestDeleteManagerDto requestDto, Long communityId);
//    ResponseGetManagerListDto getManagerList(RequestGetManagerListDto requestDto, Long communityId);
}
