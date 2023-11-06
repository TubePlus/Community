package com.example.community_service.community.application;

import com.example.community_service.community.domain.CommunityMember;
import com.example.community_service.community.infrastructure.CommunityMemberRepository;
import com.example.community_service.global.error.ErrorCode;
import com.example.community_service.global.error.handler.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommunityMemberServiceImpl {

    private final CommunityMemberRepository communityMemberRepository;

    // 유저 커뮤니티 가입
    public Integer userJoinCommunity(Long communityId, String userUuid) {

        joinCommunity(userUuid, communityId);
        return getCommunityMemberCount(communityId).intValue();
    }

    // 유저 커뮤니티 탈퇴
    public Integer userLeaveCommunity(Long communityId, String userUuid) {

        leaveCommunity(communityId, userUuid);
        return getCommunityMemberCount(communityId).intValue();
    }

    /**
     * 서비스 로직
     */

    // 커뮤니티 가입하기
    public CommunityMember joinCommunity(String userUuid, Long communityId) {

        CommunityMember joinedMember = CommunityMember.joinCommunity(communityId, userUuid);
        return communityMemberRepository.save(joinedMember);
    }

    // 커뮤니티 회원수 조회하기
    @Transactional(readOnly = true)
    public Long getCommunityMemberCount(Long communityId) {

        return communityMemberRepository.countByCommunityId(communityId);
    }

    // 커뮤니티 탈퇴
    public CommunityMember leaveCommunity(Long communityId, String userUuid) {

        return communityMemberRepository.deleteCommunityMemberByCommunityIdAndUserUuid(communityId, userUuid)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
    }
}
