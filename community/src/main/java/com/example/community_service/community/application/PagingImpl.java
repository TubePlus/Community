//package com.example.community_service.community.application;
//
//import com.example.community_service.community.dto.response.QJoinedCommunityDto;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import org.springframework.data.domain.Page;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class PagingImpl implements Paging {
//
//    private final JPAQueryFactory queryFactory;
//
//    public PagingImpl(JPAQueryFactory queryFactory) {
//        this.queryFactory = queryFactory;
//    }
//
//    @Override
//    public Page<QJoinedCommunityDto> search(SearchCondition condition) {
//        return null;
//    }
//
//
////    private final JPAQueryFactory queryFactory;
////
////    public PagingImpl(JPAQueryFactory queryFactory) {
////        this.queryFactory = queryFactory;
////    }
////
////    @Override
////    public Page<QJoinedCommunityDto> search(SearchCondition searchCondition, Pageable pageable) {
////
////        List<QJoinedCommunityDto> contents = queryFactory
////                .select(new QJoinedCommunityDto(
////
////                ))
////                .from()
////
////                return null;
////    }
//}
