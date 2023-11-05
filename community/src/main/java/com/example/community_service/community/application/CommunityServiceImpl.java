package com.example.community_service.community.application;

import com.example.community_service.community.domain.*;
import com.example.community_service.community.dto.request.*;
import com.example.community_service.community.dto.response.*;
import com.example.community_service.community.infrastructure.*;
import com.example.community_service.global.error.ErrorCode;
import com.example.community_service.global.error.handler.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
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
    private final EntityManager em; // QueryDsl

    // todo: 검색 조회 전체에 querydsl페이지네이션 구현
    /**
     * 커뮤니티 가입/탈퇴/조회
     */

    // 커뮤니티 가입하기
    @Override
    public ResponseJoinCommunityDto joinCommunity(RequestJoinCommunityDto requestDto, Long communityId) {

        // 커뮤니티 검색
        Community targetCommunity = communityRepository.findById(communityId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_RESOURCE));

        // 커뮤니티 가입
        CommunityMember communityMember = CommunityMember.joinCommunity(communityId, requestDto.getUserUuid());

        communityMemberRepository.save(communityMember);

        // 커뮤니티 회원 수 증가처리
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
        communityMember.ifPresentOrElse(communityMemberRepository::delete,
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

    // 가입한 커뮤니티 목록 조회하기
    @Transactional(readOnly = true)
    @Override
    public ResponseGetJoinedCommunityListDto getJoinedCommunityList(
            RequestGetJoinedCommunityListDto requestDto, Integer count, Integer page) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QCommunity c = QCommunity.community;
        QCommunityMember m = QCommunityMember.communityMember;

        page -= 1; // 페이지는 0부터 시작

        // todo: 쿼리 최적화 필요
        List<QJoinedCommunityDto> communityList = queryFactory
                .select(Projections.fields(QJoinedCommunityDto.class,
                        c.id.as("communityId"),
                        c.ownerUuid,
                        c.profileImage,
                        c.communityName,
                        c.description,
                        c.youtubeName,
                        c.communitySize
                ))
                .from(c)
                .join(m).on(c.id.eq(m.communityId)) // 커뮤니티 테이블과 커뮤니티 멤버 테이블 조인
                .where(m.userUuid.eq(requestDto.getUserUuid())) // 커뮤니티 멤버 데이터 중 uuid가 일치하는 데이터만 조회
                .orderBy(m.updatedDate.desc()) // 마지막 업데이트 순으로 정렬
                .offset((long) count * page) // 시작 지점
                .limit(count) // 시작 지점부터 몇 개 가져올지 설정
                .fetch();

        // 전체 갯수 카운팅
        Long countQuery = queryFactory
                .select(m.count())
                .from(m)
                .where(m.userUuid.eq(requestDto.getUserUuid()))
                .fetchFirst();

        // 페이지 갯수 카운팅
        countQuery = (long) Math.ceil((double) countQuery / count);

        return ResponseGetJoinedCommunityListDto.builder()
                .communityList(communityList)
                .pageCount(countQuery)
                .build();
    }

    /**
     * 커뮤니티 관리
     */

    // 크리에이터 커뮤니티 생성
    @Override
    public ResponseCreateCommunityDto createCommunity(
            RequestCreateCommunityDto requestDto) throws JsonProcessingException {

        // 유튜브 API로 배너/프로필이미지/채널 이름 불러오기 기능 추가하기
        HashMap<String, String> youtubeData = youtubeService.getMyChannelInfo(requestDto.getToken());

        // todo: 일단 사용하지 않는 것으로 가정하고 주석처리
//        // User 도메인 API(서버간 통신)
//        if (youtubeService.checkCreator(requestDto.getOwnerUuid())) {
//            throw new BusinessException(ErrorCode.BAD_REQUEST);
//        }

        // 커뮤니티 생성
        Community community = Community.createCommunity(
                youtubeData.get("bannerImageUrl"), youtubeData.get("profileImageUrl"),
                youtubeData.get("youtubeName"), requestDto.getCommunityName(),
                requestDto.getDescription(), requestDto.getOwnerUuid());

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

        // todo: 페이징으로 변경
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

        // 유저 밴
        BannedUser targetUser = BannedUser.banUser(
                communityId, requestDto.getTargetUuid(),
                requestDto.getBanEndDate().atTime(0,0,0));

        BannedUser bannedUser = bannedUserRepository.save(targetUser);

        // 밴 당한 유저가 매니저일 경우 권한 삭제
        Optional<CommunityManager> communityManager =
                communityManagerRepository.findByCommunityIdAndManagerUuid(communityId, requestDto.getTargetUuid());
        communityManager.ifPresent(communityManagerRepository::delete);

        return ResponseBanUserDto.builder()
                .banEndDate(bannedUser.getBanEndDate().toLocalDate())
                .bannedUserUuid(bannedUser.getBannedUuid())
                .communityId(bannedUser.getCommunityId())
                .build();
    }

    // 유저 밴 해제일 업데이트
    @Override
    public ResponseUpdateBanEndDateDto updateBanEndDate(RequestUpdateBanEndDateDto requestDto, Long communityId) {

        // 밴 유저 검색
        BannedUser bannedUser =
                bannedUserRepository.findByCommunityIdAndBannedUuid(communityId, requestDto.getTargetUuid())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

        // 유저 밴 종료일 변경
        LocalDateTime banEndDate = requestDto.getBanEndDate().atTime(0, 0, 0);
        bannedUser.updateBanEndDate(banEndDate);

        return ResponseUpdateBanEndDateDto.builder()
                .banEndDate(bannedUser.getBanEndDate().toLocalDate())
                .bannedUserUuid(bannedUser.getBannedUuid())
                .communityId(bannedUser.getCommunityId())
                .build();
    }

    // 유저 밴 해제
    @Override
    public ResponseUnbanUserDto unbanUser(RequestUnbanUserDto requestDto, Long communityId) {

        // 밴 해제할 유저 검색
        BannedUser bannedUser =
                bannedUserRepository.findByCommunityIdAndBannedUuid(communityId, requestDto.getTargetUuid())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

        // 밴 해제
        bannedUserRepository.delete(bannedUser);

        return ResponseUnbanUserDto.builder()
                .unbannedUuid(bannedUser.getBannedUuid())
                .build();
    }

    // 밴 유저 목록 조회
    @Transactional(readOnly = true)
    @Override
    public ResponseGetBannedUserListDto getBannedUserList(RequestGetBannedUserListDto requestDto, Long communityId) {

        // todo: 페이징으로 변경
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

        // 매니저 등록
        CommunityManager communityManager = CommunityManager.createManager(
                requestDto.getTargetUuid(), communityId);

        CommunityManager savedCommunityManager = communityManagerRepository.save(communityManager);

        return ResponseRegisterManagerDto.builder()
                .communityId(savedCommunityManager.getCommunityId())
                .managerUuid(savedCommunityManager.getManagerUuid())
                .build();
    }

    // 커뮤니티 매니저 삭제
    @Override
    public ResponseDeleteManagerDto deleteManager(RequestDeleteManagerDto requestDto, Long communityId) {

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

        // todo: 페이징 구현
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
