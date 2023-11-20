package com.example.community_service.community.application;

import com.example.community_service.community.domain.CommunityMember;
import com.example.community_service.community.dto.BanUserDto;
import com.example.community_service.community.dto.DeleteManagerDto;
import com.example.community_service.community.dto.request.*;
import com.example.community_service.community.dto.response.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CommunityMemberService {

    /**
     * 커뮤니티의 유저 가입/탈퇴/조회
     */
    // 유저의 커뮤니티 가입
    Integer joinCommunity(Long communityId, String userUuid);

    // 유저의 커뮤니티 탈퇴
    Integer leaveCommunity(Long communityId, String userUuid);

    // 커뮤니티 회원 생성(가입)
    void createCommunityMember(String userUuid, Long communityId);

    // 커뮤니티의 회원수 조회
    Long getCommunityMemberCount(Long communityId);

    // 커뮤니티의 특정 유저 정보 불러오기
    CommunityMember getUserInfo(Long communityId, String userUuid);

    // 커뮤니티 유저 가입 이력 조회
    Boolean checkMemberJoinHistory(Long communityId, String userUuid);

    // 커뮤니티 회원 재가입
    Integer rejoinCommunity(Long communityId, String userUuid);

    // 커뮤니티 가입 여부 확인
    Boolean checkMemberVerification(Long communityId, String userUuid);
    
    /**
     * 커뮤니티의 유저 밴 관련
     */
    // 유저 밴 처리
    BanUserDto.Response banUser(Long communityId, BanUserDto.Request requestDto);

    // 유저 밴 종료일 업데이트
    ResponseUpdateBanEndDateDto updateBanEndDate(Long communityId, RequestUpdateBanEndDateDto requestDto);

    // 유저 밴 해제
    ResponseUnbanUserDto unbanUser(Long communityId, RequestUnbanUserDto requestDto);

    // 커뮤니티 유저 밴 일자를 시간으로 변환하기
    LocalDateTime banDateConverter(LocalDate banEndDate);

    /**
     * 커뮤니티 매니저 관련
     */
    // 커뮤니티 매니저 등록 처리
    ResponseRegisterManagerDto registerManager(Long communityId, RequestRegisterManagerDto requestDto);

    // 커뮤니티 매니저 해제 처리
    DeleteManagerDto.Response deleteManager(Long communityId, DeleteManagerDto.Request requestDto);

    List<String> getUserUuidListByCommunityId(Long communityId, String boardType);
}
