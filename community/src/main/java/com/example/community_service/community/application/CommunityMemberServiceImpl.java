package com.example.community_service.community.application;

import com.example.community_service.community.domain.CommunityMember;
import com.example.community_service.community.dto.BanUserDto;
import com.example.community_service.community.dto.DeleteManagerDto;
import com.example.community_service.community.dto.request.*;
import com.example.community_service.community.dto.response.*;
import com.example.community_service.community.infrastructure.CommunityMemberRepository;
import com.example.community_service.global.error.ErrorCode;
import com.example.community_service.global.error.handler.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommunityMemberServiceImpl implements CommunityMemberService {

    private final CommunityMemberRepository communityMemberRepository;

    // 유저 커뮤니티 신규 가입
    @Override
    public Integer joinCommunity(Long communityId, String userUuid) {

        // 유저 정보 생성
        createCommunityMember(userUuid, communityId);

        // 커뮤니티 유저 수 반환
        return getCommunityMemberCount(communityId).intValue();
    }

    // 탈퇴 유저 커뮤니티 재가입
    @Override
    public Integer rejoinCommunity(Long communityId, String userUuid) {

        // 유저 정보 불러오기
        CommunityMember member = getUserInfo(communityId, userUuid);

        // 해당 유저 활성화 처리
        member.rejoinCommunity();

        // 커뮤니티 유저 수 반환
        return getCommunityMemberCount(communityId).intValue();
    }

    // 유저 커뮤니티 탈퇴(커뮤니티 내 계정 비활성화 처리)
    @Override
    public Integer leaveCommunity(Long communityId, String userUuid) {

        // 유저 정보 불러오기
        CommunityMember member = getUserInfo(communityId, userUuid);

        // 해당 유저 비활성화 처리
        member.leaveCommunity();

        // 커뮤니티 유저 수 반환
        return getCommunityMemberCount(communityId).intValue();
    }

    // 유저 밴 처리
    @Override
    public BanUserDto.Response banUser(Long communityId, BanUserDto.Request requestDto) {

        // 유저 정보 불러오기
        CommunityMember member = getUserInfo(communityId, requestDto.getTargetUuid());

        // 밴 일자를 시간으로 변환
        LocalDateTime banEndDateTime = banDateConverter(requestDto.getBanEndDate());

        // 유저 밴 상태로 변경
        member.banUser(banEndDateTime);

        return BanUserDto.Response.formResponseDto(member.getCommunityId(), member.getUserUuid(),
                requestDto.getBanEndDate());
    }

    // 유저 밴 해제일 업데이트
    @Override
    public ResponseUpdateBanEndDateDto updateBanEndDate(Long communityId, RequestUpdateBanEndDateDto requestDto) {

        // 유저 불러오기
        CommunityMember member = getUserInfo(communityId, requestDto.getTargetUuid());

        // 밴 일자를 시간으로 변환
        LocalDateTime banEndDateTime = banDateConverter(requestDto.getBanEndDate());

        // 해당 유저의 밴 종료일자 업데이트
        member.updateBanEndDate(banEndDateTime);

        return ResponseUpdateBanEndDateDto.formResponseDto(
                member.getCommunityId(), member.getUserUuid(), requestDto.getBanEndDate());
    }

    // 유저 밴 해제
    @Override
    public ResponseUnbanUserDto unbanUser(Long communityId, RequestUnbanUserDto requestDto) {

        // 유저 불러오기
        CommunityMember member = getUserInfo(communityId, requestDto.getTargetUuid());

        // 불러온 유저의 밴 상태 해제
        member.unbanUser();

        return ResponseUnbanUserDto.formResponseDto(member.getUserUuid());
    }

    // 커뮤니티 매니저 추가
    @Override
    public ResponseRegisterManagerDto registerManager(Long communityId, RequestRegisterManagerDto requestDto) {

        // 유저 불러오기
        CommunityMember member = getUserInfo(communityId, requestDto.getTargetUuid());

        // 불러온 유저 매니저 권한 부여 처리
        member.grantManagerAuthority();

        return ResponseRegisterManagerDto.formResponseDto(member.getCommunityId(), member.getUserUuid());
    }

    // 커뮤니티 매니저 삭제
    @Override
    public DeleteManagerDto.Response deleteManager(Long communityId, DeleteManagerDto.Request requestDto) {

        // 유저 불러오기
        CommunityMember member = getUserInfo(communityId, requestDto.getTargetUuid());

        // 불러온 유저 매니저 권한 삭제 처리
        member.takeManagerAuthority();

        return DeleteManagerDto.Response.formResponseDto(member.getUserUuid(), member.getCommunityId());
    }

    /**
     * 서비스 로직(재사용성 높은 로직)
     */

    // 커뮤니티 회원 생성
    @Override
    public void createCommunityMember(String userUuid, Long communityId) {

        CommunityMember joinedMember = CommunityMember.joinCommunity(
                communityId, userUuid, false, false, false, true);
        communityMemberRepository.save(joinedMember);
    }

    // 커뮤니티 회원수 조회
    @Override
    @Transactional(readOnly = true)
    public Long getCommunityMemberCount(Long communityId) {

        return communityMemberRepository.countByCommunityIdAndAndIsActiveTrue(communityId);
    }

    // 유저 가입 이력 확인
    @Override
    @Transactional(readOnly = true)
    public Boolean checkMemberJoinHistory(Long communityId, String userUuid) {

        return communityMemberRepository.existsByCommunityIdAndUserUuid(communityId, userUuid);
    }

    // 유저 정보 불러오기
    @Override
    public CommunityMember getUserInfo(Long communityId, String userUuid) {

        return communityMemberRepository.findByCommunityIdAndUserUuid(communityId, userUuid)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
    }

    // 유저 밴 일자를 시간으로 변환하기
    @Override
    public LocalDateTime banDateConverter(LocalDate banEndDate) {

        // 예: 밴 일자를 12월 25일로 설정할 경우 밴 해제시간을 12월25일 23시59분59초로 설정
        return banEndDate.atTime(23, 59, 59);
    }

    // todo: 미완성
    // 커뮤니티 회원 리스트 조회
//    @Override
//    public List<CommunityMember> getCommunityMemberList(Long communityId) {
//
//        return communityMemberRepository.findAllByCommunityId(communityId);
//    }
}
