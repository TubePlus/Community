package com.example.community_service.community.application;

import com.example.community_service.community.domain.*;
import com.example.community_service.community.dto.request.*;
import com.example.community_service.community.dto.response.*;
import com.example.community_service.community.infrastructure.*;
import com.example.community_service.global.error.ErrorCode;
import com.example.community_service.global.error.handler.BusinessException;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommunityServiceImpl {

    private final CommunityRepository communityRepository;
    private final CommunityManagerRepository communityManagerRepository;
    private final BannedUserRepository bannedUserRepository;
    private final CommunityMemberRepository communityMemberRepository;
    private final EntityManager em; // QueryDsl

    // todo: 검색 조회 전체에 querydsl페이지네이션 구현

    /**
     * 서비스 로직
     * */
    // 커뮤니티 검색
    @Transactional(readOnly = true)
    public Community searchCommunity(Long communityId) {

        return communityRepository.findById(communityId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_RESOURCE));
    }

    // 커뮤니티 회원수 업데이트
    public void updateCommunityMemberCount(Long communityId, Integer memberCount) {

        Optional<Community> community = communityRepository.findById(communityId);
        community.ifPresentOrElse(
                c -> c.updateCommunityMemberCount(memberCount),
                () -> {
                    throw new BusinessException(ErrorCode.NOT_FOUND_RESOURCE);
                }
        );
    }

    // todo: 따로 빼야할지도? 커뮤니티랑 커뮤니티맴버 둘다 사용하기 때문에
    // 가입한 커뮤니티 목록 조회하기
    @Transactional(readOnly = true)

    public ResponseGetJoinedCommunityListDto getJoinedCommunityList(
            String userUuid, Integer count, Integer page) {

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
                        c.communityMemberCount
                ))
                .from(c)
                .join(m).on(c.id.eq(m.communityId)) // 커뮤니티 테이블과 커뮤니티 멤버 테이블 조인
                .where(m.userUuid.eq(userUuid)) // 커뮤니티 멤버 데이터 중 uuid가 일치하는 데이터만 조회
                .orderBy(m.updatedDate.desc()) // 마지막 업데이트 순으로 정렬
                .offset((long) count * page) // 시작 지점
                .limit(count) // 시작 지점부터 몇 개 가져올지 설정
                .fetch();

        // 전체 갯수 카운팅
        Long countQuery = queryFactory
                .select(m.count())
                .from(m)
                .where(m.userUuid.eq(userUuid))
                .fetchFirst();

        // 페이지 갯수 카운팅
        Long pageCount = (long) Math.ceil((double) countQuery / count);

        return ResponseGetJoinedCommunityListDto.builder()
                .communityList(communityList)
                .pageCount(pageCount)
                .build();
    }

    // 커뮤니티 상세 조회하기
    @Transactional(readOnly = true)

    public ResponseGetCommunityInfoDto getCommunityInfo(Long communityId) {

        // 커뮤니티 검색
        Community targetCommunity = searchCommunity(communityId);

        return ResponseGetCommunityInfoDto.builder()
                .ownerUuid(targetCommunity.getOwnerUuid())
                .communityId(targetCommunity.getId())
                .communityName(targetCommunity.getCommunityName())
                .description(targetCommunity.getDescription())
                .profileImage(targetCommunity.getProfileImage())
                .bannerImage(targetCommunity.getBannerImage())
                .youtubeName(targetCommunity.getYoutubeName())
                .communityMemberCount(targetCommunity.getCommunityMemberCount())
                .createdDate(targetCommunity.getCreatedDate().toLocalDate())
                .updatedDate(targetCommunity.getUpdatedDate().toLocalDate())
                .build();
    }

    // 커뮤니티 가입하기
//    public ResponseJoinCommunityDto joinCommunity(RequestJoinCommunityDto requestDto, Long communityId) {
//
//        // 커뮤니티 검색
//        Community targetCommunity = communityRepository.findById(communityId)
//                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_RESOURCE));
//
//        // 커뮤니티 가입
//        CommunityMember communityMember = CommunityMember.joinCommunity(communityId, requestDto.getUserUuid());
//        communityMemberRepository.save(communityMember);
//
//        // 커뮤니티 회원 수 업데이트
//        targetCommunity.updateCommunityMemberCount(
//                communityMemberRepository.countByCommunityId(communityId).intValue());
//
//        return ResponseJoinCommunityDto.builder()
//                .communityId(communityMember.getCommunityId())
//                .userUuid(communityMember.getUserUuid())
//                .build();
//    }

    // 커뮤니티 탈퇴하기

//    public ResponseLeaveCommunityDto leaveCommunity(RequestLeaveCommunityDto requestDto, Long communityId) {
//
//        // 커뮤니티 검색
//        Community targetCommunity = communityRepository.findById(communityId)
//                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_RESOURCE));
//
//        // 커뮤니티 탈퇴
//        CommunityMember deletedMember =
//                communityMemberRepository.deleteCommunityMemberByCommunityIdAndUserUuid(
//                        communityId, requestDto.getUserUuid())
//                        .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
//
//        // 커뮤니티 회원 수 업데이트
//        targetCommunity.updateCommunityMemberCount(
//                communityMemberRepository.countByCommunityId(communityId).intValue());
//
//        return ResponseLeaveCommunityDto.builder()
//                .userUuid(deletedMember.getUserUuid())
//                .communityId(deletedMember.getCommunityId())
//                .build();
//    }

    /**
     * 커뮤니티 관리
     */

    // 크리에이터 커뮤니티 생성

    public ResponseCreateCommunityDto createCommunity(RequestCreateCommunityDto requestDto) {

        // 커뮤니티 생성
        Community community = Community.createCommunity(
                requestDto.getBannerImage(), requestDto.getProfileImage(), requestDto.getYoutubeName(),
                requestDto.getCommunityName(), requestDto.getDescription(), requestDto.getOwnerUuid(), 1);

        Community savedCommunity = communityRepository.save(community);

        return ResponseCreateCommunityDto.builder()
                .communityId(savedCommunity.getId())
                .build();
    }

    // 커뮤니티 정보 수정
    public ResponseUpdateCommunityDto updateCommunity(
            RequestUpdateCommunityDto requestDto, Long communityId) {

        // 커뮤니티 객체 불러오기
        Community targetCommunity = searchCommunity(communityId);

        // 커뮤니티 정보 업데이트
        targetCommunity.updateCommunity(
                requestDto.getCommunityName(), requestDto.getDescription(),
                requestDto.getProfileImage(), requestDto.getBannerImage());

        return ResponseUpdateCommunityDto.builder()
                .communityId(targetCommunity.getId())
                .build();
    }

    // 커뮤니티 가입 회원 조회

    public ResponseGetCommunityMemberListDto getCommunityMemberList(Long communityId) {

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
