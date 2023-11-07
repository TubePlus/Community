package com.example.community_service.side.application;

import com.example.community_service.side.dto.CommunitySideDto;
import com.example.community_service.side.vo.response.GetCommunitySideResponse;

import java.util.List;

public interface CommunitySideService {
    // 생성
    Long createCommunitySide(CommunitySideDto communitySideDto);
    // 수정
    void updateCommunitySide(Long communitySideId, CommunitySideDto communitySideDto, Long communityId);
    // 삭제
    void deleteCommunitySide(Long communitySideId, Long communityId);
    // 조회
    List<GetCommunitySideResponse> getCommunitySide(Long communityId);
}
