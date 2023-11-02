package com.example.community_service.community.application;

import com.example.community_service.community.domain.BannedUser;
import com.example.community_service.community.domain.Community;
import com.example.community_service.community.domain.CommunityManager;
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
import java.util.Optional;

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

    /**
     * 커뮤니티 가입/탈퇴/조회
     */

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

    // 커뮤니티 탈퇴하기
    @Override
    public ResponseLeaveCommunityDto leaveCommunity(RequestLeaveCommunityDto requestDto, Long communityId) {

        // 커뮤니티 검색
        Community targetCommunity = communityRepository.findById(communityId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_RESOURCE));

        // 커뮤니티 탈퇴
        Optional<CommunityMember> communityMember =
                communityMemberRepository.findByCommunityIdAndUserUuid(communityId, requestDto.getUserUuid());
        communityMember.ifPresentOrElse(
                communityMemberRepository::delete,
                () -> {throw new BusinessException(ErrorCode.NOT_FOUND_USER);}
        );

        // 매니저일 경우 매니저 권한 삭제
        Optional<CommunityManager> communityManager =
                communityManagerRepository.findByCommunityIdAndManagerUuid(communityId, requestDto.getUserUuid());
        communityManager.ifPresent(communityManagerRepository::delete);

        // 커뮤니티 회원 수 감소
        targetCommunity.decreaseCommunitySize();

        return ResponseLeaveCommunityDto.builder()
                .userUuid(requestDto.getUserUuid())
                .communityId(targetCommunity.getId())
                .build();
    }

    /**
     * 커뮤니티 관리
     */

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

    // 커뮤니티 가입 회원 조회
    @Override
    public ResponseGetCommunityMemberListDto getCommunityMemberList(
            RequestGetCommunityMemberListDto requestDto, Long communityId) {

        // 커뮤니티 권한 확인
        checkManagerOrCreator(communityId, requestDto.getManagerUuid());

        // 커뮤니티 멤버 조회
        List<CommunityMember> communityMemberList =
                communityMemberRepository.findAllByCommunityId(communityId);

        return ResponseGetCommunityMemberListDto.builder()
                .communityMemberList(communityMemberList)
                .build();
    }

    /**
     * 커뮤니티 밴유저 관리
     */

    // 유저 밴
    @Override
    public ResponseBanUserDto banUser(RequestBanUserDto requestDto, Long communityId) {

        // 커뮤니티 권한 확인
        checkManagerOrCreator(communityId, requestDto.getManagerUuid());

        // 이미 밴 된 유저인지 확인
        if (bannedUserRepository.existsByCommunityIdAndBannedUuid(communityId, requestDto.getTargetUuid())) {
            throw new BusinessException(ErrorCode.DUPLICATE_RESOURCE);
        }

        // 유저 밴
        LocalDateTime banEndDate = LocalDateTime.now().plusDays(requestDto.getBanDays());
        BannedUser targetUser = BannedUser.builder()
                .communityId(communityId)
                .bannedUuid(requestDto.getTargetUuid())
                .banEndDate(banEndDate)
                .build();

        BannedUser bannedUser = bannedUserRepository.save(targetUser);

        // 매니저일 경우 권한 삭제
        Optional<CommunityManager> communityManager =
                communityManagerRepository.findByCommunityIdAndManagerUuid(communityId, requestDto.getTargetUuid());
        communityManager.ifPresent(communityManagerRepository::delete);

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
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

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

    /**
     * 커뮤니티 매니저 관리
     */

    // 커뮤니티 매니저 등록
    @Override
    public ResponseRegisterManagerDto registerManager(RequestRegisterManagerDto requestDto, Long communityId) {

        // 크리에이터 권한 확인
        checkCreator(communityId, requestDto.getCreatorUuid());

        // 이미 매니저인지 확인
        if (communityManagerRepository.existsByCommunityIdAndManagerUuid(communityId, requestDto.getTargetUuid())) {
            throw new BusinessException(ErrorCode.DUPLICATE_RESOURCE);
        }

        // 밴유저이면 등록 불가능
        if (bannedUserRepository.existsByCommunityIdAndBannedUuid(communityId, requestDto.getTargetUuid())) {
            throw new BusinessException(ErrorCode.BAD_REQUEST);
        }

        // 매니저 등록
        CommunityManager communityManager = CommunityManager.builder()
                .communityId(communityId)
                .managerUuid(requestDto.getTargetUuid())
                .build();

        CommunityManager savedCommunityManager = communityManagerRepository.save(communityManager);

        return ResponseRegisterManagerDto.builder()
                .communityId(savedCommunityManager.getCommunityId())
                .managerUuid(savedCommunityManager.getManagerUuid())
                .build();
    }

    // 커뮤니티 매니저 삭제
    @Override
    public ResponseDeleteManagerDto deleteManager(RequestDeleteManagerDto requestDto, Long communityId) {

        // 크리에이터 권한 확인
        checkCreator(communityId, requestDto.getCreatorUuid());

        // 매니저 여부 확인 후 매니저 삭제
        CommunityManager targetManager = communityManagerRepository.findByCommunityIdAndManagerUuid(
                communityId, requestDto.getTargetUuid())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

        communityManagerRepository.delete(targetManager);

        return ResponseDeleteManagerDto.builder()
                .communityId(targetManager.getCommunityId())
                .managerUuid(targetManager.getManagerUuid())
                .build();
    }

    // 커뮤니티 매니저 목록 조회
    @Transactional(readOnly = true)
    @Override
    public ResponseGetManagerListDto getManagerList(RequestGetManagerListDto requestDto, Long communityId) {

        // 크리에이터 권한 확인
        checkCreator(communityId, requestDto.getCreatorUuid());

        List<CommunityManager> managerList = communityManagerRepository.findAllByCommunityId(communityId);

        return ResponseGetManagerListDto.builder()
                .managerList(managerList)
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
        }
    }

    // 크리에이터 권한 확인
    public void checkCreator(Long communityId, String uuid) {

        // 커뮤니티id에 해당하는 커뮤니티 조회
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_RESOURCE));

        // 커뮤니티의 크리에이터인지 확인
        if(!(community.getOwnerUuid().equals(uuid))) {
            throw new BusinessException(ErrorCode.BAD_REQUEST);
        }
    }
}
