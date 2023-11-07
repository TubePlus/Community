package com.example.community_service.side.application;

import com.example.community_service.side.vo.request.DropdownContentCreateRequest;

import java.util.List;

public interface DropdownService {
    // 생성
    void createDropdownContent(List<DropdownContentCreateRequest> dropdown, Long communitySideId);
    // 삭제
    void deleteDropdownContent(List<Long> dropdownContentId);
}
