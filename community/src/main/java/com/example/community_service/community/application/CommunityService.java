package com.example.community_service.community.application;

import com.example.community_service.community.domain.Community;
import com.example.community_service.community.dto.CreateCommunityDto;
import com.example.community_service.community.dto.request.*;
import com.example.community_service.community.dto.response.*;

import java.util.List;

public interface CommunityService { // todo: 인터페이스 도메인별로 구분해서 나누기

    /**
     * 커뮤니티 생성/삭제 관련
     */

    // 커뮤니티 생성
    CreateCommunityDto.Response createCommunity(CreateCommunityDto.Request requestDto);

    // 커뮤니티 이름 중복 확인
    Boolean isCommunityNameDuplicate(String communityName);

    /**
     * 커뮤니티 조회 관련
     */
    
    // 커뮤니티 정보 상세 조회
    ResponseGetCommunityInfoDto getCommunityInfo(Long communityId);

    // 커뮤니티 불러오기
    Community searchCommunity(Long communityId);

    // 해당 유저의 커뮤니티 소유여부 확인
    Boolean checkCommunityExistence(String userUuid);

    // 해당 유저의 커뮤니티 id 조회
    Long getCommunityIdByOwnerUuid(String userUuid);

    /**
     * 커뮤니티 업데이트 관련
     */
    
    // 커뮤니티 회원수 업데이트
    void updateCommunityMemberCount(Long communityId, Integer memberCount);

    // 커뮤니티 정보 수정
    ResponseUpdateCommunityDto updateCommunity(RequestUpdateCommunityDto requestDto, Long communityId);

    /**
     * 기타
     */
    String getOwnerUuidByCommunityId(Long communityId);
}
