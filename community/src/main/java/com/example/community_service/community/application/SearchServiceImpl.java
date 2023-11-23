package com.example.community_service.community.application;

import com.example.community_service.community.domain.Community;
import com.example.community_service.community.domain.QCommunity;
import com.example.community_service.community.domain.QCommunityMember;
import com.example.community_service.community.dto.*;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

        // 정렬 정보를 얻어오기
//        List<OrderSpecifier<?>> orderSpecifiers = getOrderSpecifiers(qCommunity, pageable.getSort());

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
                        .limit(pageable.getPageSize())
                        .offset(pageable.getOffset())
                        .fetch();

        JPAQuery<Long> count = queryFactory.select(qCommunityMember.count())
                .from(qCommunityMember)
                .where(qCommunityMember.userUuid.eq(requestDto.getUserUuid()));

        return PageableExecutionUtils.getPage(fetch, pageable, count::fetchOne);
    }

//    private List<OrderSpecifier> getAllOrderSpecifiers(Pageable pageable) {
//
//        List<OrderSpecifier> ORDERS = new ArrayList<>();
//
//        if (!isEmpty(pageable.getSort())) {
//            for (Sort.Order order : pageable.getSort()) {
//                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
//                switch (order.getProperty()) {
//                    case "id":
//                        OrderSpecifier<?> orderId = QueryDslUtil.getSortedColumn(direction, QRoom.room, "id");
//                        ORDERS.add(orderId);
//                        break;
//                    case "user":
//                        OrderSpecifier<?> orderUser = QueryDslUtil.getSortedColumn(direction, QUser.user, "name");
//                        ORDERS.add(orderUser);
//                        break;
//                    case "category":
//                        OrderSpecifier<?> orderCategory = QueryDslUtil.getSortedColumn(direction, QRoom.room, "category");
//                        ORDERS.add(orderCategory);
//                        break;
//                    default:
//                        break;
//                }
//            }
//        }
//
//        return ORDERS;
//    }
//    // OrderSpecifier 리스트를 반환하는 메서드 추가
//    private List<OrderSpecifier<?>> getOrderSpecifiers(QCommunity qCommunity, Sort sort) {
//        return sort.stream()
//                .map(order -> {
//                    ComparableExpressionBase<?> comparableExpressionBase;
//                    if ("id".equals(order.getProperty())) {
//                        comparableExpressionBase = qCommunity.id;
//                    } else if ("ownerUuid".equals(order.getProperty())) {
//                        comparableExpressionBase = qCommunity.ownerUuid;
//                    } else if ("profileImage".equals(order.getProperty())) {
//                        comparableExpressionBase = qCommunity.profileImage;
//                    } else if ("bannerImage".equals(order.getProperty())) {
//                        comparableExpressionBase = qCommunity.bannerImage;
//                    } else if ("communityName".equals(order.getProperty())) {
//                        comparableExpressionBase = qCommunity.communityName;
//                    } else if ("description".equals(order.getProperty())) {
//                        comparableExpressionBase = qCommunity.description;
//                    } else if ("youtubeName".equals(order.getProperty())) {
//                        comparableExpressionBase = qCommunity.youtubeName;
//                    } else if ("communityMemberCount".equals(order.getProperty())) {
//                        comparableExpressionBase = qCommunity.communityMemberCount;
//                    } else if ("createdDate".equals(order.getProperty())) {
//                        comparableExpressionBase = qCommunity.createdDate;
//                    } else if ("updatedDate".equals(order.getProperty())) {
//                        comparableExpressionBase = qCommunity.updatedDate;
//                    }
//
//                    else {
//                        // 기본적으로 문자열로 처리
//                        comparableExpressionBase = Expressions.stringPath(qCommunity, order.getProperty());
//                    }
//
//                    return order.isAscending() ? comparableExpressionBase.asc() : comparableExpressionBase.desc();
//                })
//                .toList();
//    }
    
    // todo: 최적화하기
    // 커뮤니티에 가입된 유저 모두 조회
    @Override
//    @Cacheable(value = "communityMembers", key = "{#communityId, #pageable.pageNumber, #pageable.pageSize}",
//            cacheManager = "redisCacheManager")

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
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy()
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

    // 해당 커뮤니티에서 밴 된 유저 모두 조회
    @Override
    public Page<GetBannedMemberListDto.Response> getAllBannedMembers(Long communityId, Pageable pageable) {

        QCommunityMember qCommunityMember = new QCommunityMember("communityMember");

        List<GetBannedMemberListDto.Response> fetch = queryFactory.select(
                Projections.fields(GetBannedMemberListDto.Response.class,
                        qCommunityMember.userUuid,
                        qCommunityMember.createdDate,
                        qCommunityMember.updatedDate,
                        qCommunityMember.banEndDate,
                        qCommunityMember.isMembershipUser))
                .from(qCommunityMember)
                .where(qCommunityMember.communityId.eq(communityId).and(qCommunityMember.isBanned.eq(true)))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> Count = queryFactory.select(qCommunityMember.count())
                .from(qCommunityMember)
                .where(qCommunityMember.communityId.eq(communityId).and(qCommunityMember.isBanned.eq(true)));

        return PageableExecutionUtils.getPage(fetch, pageable, Count::fetchOne);
    }

    // 해당 커뮤니티의 매니저 모두 조회
    @Override
    public Page<GetManagerListDto.Response> getAllManagers(Long communityId, Pageable pageable) {

        QCommunityMember qCommunityMember = new QCommunityMember("communityMember");

        List<GetManagerListDto.Response> fetch = queryFactory.select(
                Projections.fields(GetManagerListDto.Response.class,
                        qCommunityMember.userUuid,
                        qCommunityMember.createdDate,
                        qCommunityMember.updatedDate,
                        qCommunityMember.isMembershipUser))
                .from(qCommunityMember)
                .where(qCommunityMember.communityId.eq(communityId)
                        .and(qCommunityMember.isManager.eq(true))
                        .and(qCommunityMember.isActive.eq(true))
                )
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> Count = queryFactory.select(qCommunityMember.count())
                .from(qCommunityMember)
                .where(qCommunityMember.communityId.eq(communityId)
                        .and(qCommunityMember.isManager.eq(true))
                        .and(qCommunityMember.isActive.eq(true))
                );

        return PageableExecutionUtils.getPage(fetch, pageable, Count::fetchOne);
    }

    // 서비스 내 모든 커뮤니티에서 n개의 커뮤니티 랜덤반환
    @Override
    public List<Community> getRandomCommunities(Integer size) {

        QCommunity qCommunity = new QCommunity("community");

        return queryFactory.selectFrom(qCommunity)
                .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
                .limit(size) //몇개를 뽑아올건지
                .fetch();
    }
}
