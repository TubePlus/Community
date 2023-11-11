package com.example.community_service.community.application;

import com.example.community_service.community.domain.QCommunity;
import com.example.community_service.community.domain.QCommunityMember;
import com.example.community_service.community.dto.GetCommunitiesMatchingUuidsDto;
import com.example.community_service.community.dto.GetCommunityMemberListDto;
import com.example.community_service.community.dto.GetJoinedCommunitiesDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchServiceImpl implements SearchService {

    private final JPAQueryFactory queryFactory;

    // 내가 가입한 커뮤니티 전체 조회
    @Override
    public Page<GetJoinedCommunitiesDto.Response> getAllJoinedCommunities(
            GetJoinedCommunitiesDto.Request requestDto, Pageable pageable) {

        // 동시성 문제 해결 위해서 매번 QClass 인스턴스화
        QCommunity qCommunity = new QCommunity("community");
        QCommunityMember qCommunityMember = new QCommunityMember("communityMember");

        List<GetJoinedCommunitiesDto.Response> fetch =
                queryFactory.select(Projections.fields(GetJoinedCommunitiesDto.Response.class,
                        qCommunity.id.as("communityId"),
                        qCommunity.ownerUuid,
                        qCommunity.profileImage,
                        qCommunity.communityName,
                        qCommunity.description,
                        qCommunity.youtubeName,
                        qCommunity.communityMemberCount
                ))
                .from(qCommunity)
                .join(qCommunityMember).on(qCommunity.id.eq(qCommunityMember.communityId))
                .where(qCommunityMember.userUuid.eq(requestDto.getUserUuid()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = queryFactory.select(qCommunityMember.count())
                .from(qCommunityMember)
                .where(qCommunityMember.userUuid.eq(requestDto.getUserUuid()));

        return PageableExecutionUtils.getPage(fetch, pageable, count::fetchOne);
    }
    
    // todo: 최적화하기
    // 커뮤니티에 가입된 유저 모두 조회
    @Override
    public Page<GetCommunityMemberListDto.Response> getAllCommunityMembers(
            Long communityId, Pageable pageable) {

        // 동시성 문제 해결 위해서 매번 QClass 인스턴스화
        QCommunityMember qCommunityMember = new QCommunityMember("communityMember");

        List<GetCommunityMemberListDto.Response> fetch = queryFactory.select(
                Projections.fields(GetCommunityMemberListDto.Response.class,
                        qCommunityMember.userUuid,
                        qCommunityMember.isBanned,
                        qCommunityMember.isManager,
                        qCommunityMember.createdDate,
                        qCommunityMember.updatedDate))
                .from(qCommunityMember)
                .where(qCommunityMember.communityId.eq(communityId).and(qCommunityMember.isActive.eq(true)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = queryFactory.select(qCommunityMember.count())
                .from(qCommunityMember)
                .where(qCommunityMember.communityId.eq(communityId).and(qCommunityMember.isActive.eq(true)));

        return PageableExecutionUtils.getPage(fetch, pageable, count::fetchOne);
    }

    // 리스트의 각 uuid에 해당하는 커뮤니티 정보 불러오기
    @Override
    public List<GetCommunitiesMatchingUuidsDto.Response> getCommunitiesMatchingUuids(List<String> uuidList) {

        QCommunity qCommunity = new QCommunity("community");
        List<GetCommunitiesMatchingUuidsDto.Response> communityDataList = new ArrayList<>();
        
        // 각 uuid에 해당하는 커뮤니티 정보 불러오기

        return queryFactory.select(
                Projections.fields(GetCommunitiesMatchingUuidsDto.Response.class,
                        qCommunity.id.as("communityId"),
                        qCommunity.ownerUuid,
                        qCommunity.profileImage,
                        qCommunity.communityName,
                        qCommunity.youtubeName,
                        qCommunity.communityMemberCount
                ))
                .from(qCommunity)
                .where(qCommunity.ownerUuid.in(uuidList))
                .fetch();
    }
}
