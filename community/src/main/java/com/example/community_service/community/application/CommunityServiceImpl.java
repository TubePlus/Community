package com.example.community_service.community.application;

import com.example.community_service.community.domain.BannedUser;
import com.example.community_service.community.domain.Community;
import com.example.community_service.community.domain.CommunityMember;
import com.example.community_service.community.dto.request.*;
import com.example.community_service.community.dto.response.*;
import com.example.community_service.community.infrastructure.BannedUserRepository;
import com.example.community_service.community.infrastructure.CommunityManagerRepository;
import com.example.community_service.community.infrastructure.CommunityMemberRepository;
import com.example.community_service.community.infrastructure.CommunityRepository;
import com.example.community_service.global.error.ErrorCode;
import com.example.community_service.global.error.handler.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepository communityRepository;
    private final CommunityManagerRepository communityManagerRepository;
    private final BannedUserRepository bannedUserRepository;
    private final CommunityMemberRepository communityMemberRepository;
    private final YoutubeService youtubeService;

    // 크리에이터 커뮤니티 생성
    @Override
    public ResponseCreateCommunityDto createCommunity(
            RequestCreateCommunityDto requestDto) throws JsonProcessingException {

        // 크리에이터가 아닐 시 에러 처리
        if (!requestDto.getIsCreator()) {
            throw new BusinessException(ErrorCode.BAD_REQUEST);
        }

        // 이미 커뮤니티 존재 시에 에러 처리
        if (communityRepository.existsByOwnerUuid(requestDto.getOwnerUuid())) {
            throw new BusinessException(ErrorCode.DUPLICATE_RESOURCE);
        }

        // 유튜브 API로 배너/프로필이미지/채널 이름 불러오기 기능 추가하기
        HashMap<String, String> youtubeData = youtubeService.getMyChannelInfo(requestDto.getToken());

        // todo: 일단 사용하지 않는 것으로 가정하고 주석처리
//        // User 도메인 API(서버간 통신)
//        if (youtubeService.checkCreator(requestDto.getOwnerUuid())) {
//            throw new BusinessException(ErrorCode.BAD_REQUEST);
//        }

        Community community = Community.builder()
                .communitySize(1) // 커뮤니티 생성 시 크리에이터 1명이므로 1로 설정
                .bannerImage(youtubeData.get("bannerImageUrl"))
                .profileImage(youtubeData.get("profileImageUrl"))
                .youtubeName(youtubeData.get("youtubeName"))
                .communityName(requestDto.getCommunityName())
                .description(requestDto.getDescription())
                .ownerUuid(requestDto.getOwnerUuid())
                .build();

        Community savedCommunity = communityRepository.save(community);

        return ResponseCreateCommunityDto.builder()
                .communityId(savedCommunity.getId())
                .build();
    }

    // 커뮤니티 정보 수정
    @Override
    public ResponseUpdateCommunityDto updateCommunity(
            RequestUpdateCommunityDto requestDto, Long communityId) {

        // 커뮤니티 검색
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_RESOURCE));

        // 커뮤니티 권한 확인(크리에이터만 가능)
        if (!(community.getOwnerUuid().equals(requestDto.getOwnerUuid()))) {
            throw new BusinessException(ErrorCode.BAD_REQUEST);
        }

        community.updateCommunity(
                requestDto.getCommunityName(), requestDto.getDescription(),
                requestDto.getProfileImage(), requestDto.getBannerImage());

        return ResponseUpdateCommunityDto.builder()
                .communityId(community.getId())
                .build();
    }

    // 유저 밴
    @Override
    public ResponseBanUserDto banUser(RequestBanUserDto requestDto, Long communityId) {

        // todo: 이미 밴된 유저인지 확인하는 로직 필요

        // 커뮤니티 권한 확인
        checkManagerOrCreator(communityId, requestDto.getManagerUuid());

        // 유저 밴
        LocalDateTime banEndDate = LocalDateTime.now().plusDays(requestDto.getBanDays());
        BannedUser targetUser = BannedUser.builder()
                .communityId(communityId)
                .bannedUuid(requestDto.getTargetUuid())
                .banEndDate(banEndDate)
                .build();

        BannedUser bannedUser = bannedUserRepository.save(targetUser);

        return ResponseBanUserDto.builder()
                .banEndDate(bannedUser.getBanEndDate().toLocalDate())
                .bannedUserUuid(bannedUser.getBannedUuid())
                .communityId(bannedUser.getCommunityId())
                .build();
    }

    // 유저 밴 해제
    @Override
    public ResponseUnbanUserDto unbanUser(RequestUnbanUserDto requestDto, Long communityId) {

        // 커뮤니티 권한 확인
        checkManagerOrCreator(communityId, requestDto.getManagerUuid());

        // 밴 해제할 유저 검색
        BannedUser bannedUser =
                bannedUserRepository.findByCommunityIdAndBannedUuid(communityId, requestDto.getTargetUuid())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_RESOURCE));

        bannedUserRepository.delete(bannedUser);

        return ResponseUnbanUserDto.builder()
                .unbannedUuid(bannedUser.getBannedUuid())
                .build();
    }

    // 밴 유저 목록 조회
    @Transactional(readOnly = true)
    @Override
    public ResponseGetBannedUserListDto getBannedUserList(RequestGetBannedUserListDto requestDto, Long communityId) {

        // 커뮤니티 권한 확인
        checkManagerOrCreator(communityId, requestDto.getManagerUuid());

        // 밴 당한 유저 목록 불러오기
        List<BannedUser> bannedUserList = bannedUserRepository.findAllByCommunityId(communityId);

        return ResponseGetBannedUserListDto.builder()
                .bannedUserList(bannedUserList)
                .build();
    }

    // 커뮤니티 가입하기
    @Override
    public ResponseJoinCommunityDto joinCommunity(RequestJoinCommunityDto requestDto, Long communityId) {

        // 커뮤니티 검색
        Community targetCommunity = communityRepository.findById(communityId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_RESOURCE));

        // 이미 가입한 유저인지 확인
        if(communityMemberRepository.existsByCommunityIdAndUserUuid(communityId, requestDto.getUserUuid())) {
            throw new BusinessException(ErrorCode.DUPLICATE_RESOURCE);
        }

        // 커뮤니티 가입
        CommunityMember communityMember = CommunityMember.builder()
                .communityId(communityId)
                .userUuid(requestDto.getUserUuid())
                .build();

        communityMemberRepository.save(communityMember);

        // 커뮤니티 회원 수 증가
        targetCommunity.increaseCommunitySize();

        return ResponseJoinCommunityDto.builder()
                .communityId(targetCommunity.getId())
                .userUuid(communityMember.getUserUuid())
                .build();
    }

    /**
     * 비즈니스 로직
     */

    // 매니저/크리에이터 권한 확인
    public void checkManagerOrCreator(Long communityId, String uuid) {

        // 커뮤니티id에 해당하는 커뮤니티 조회
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_RESOURCE));

        // 커뮤니티의 매니저거나 크리에이터인지 확인
        if(!(communityManagerRepository.existsByCommunityIdAndManagerUuid(communityId, uuid)
                || community.getOwnerUuid().equals(uuid))
        ) {
            throw new BusinessException(ErrorCode.BAD_REQUEST);
        };
    }
}
