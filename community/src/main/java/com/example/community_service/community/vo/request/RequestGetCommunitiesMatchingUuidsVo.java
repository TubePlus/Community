package com.example.community_service.community.vo.request;

import lombok.Getter;

import java.util.List;

@Getter
public class RequestGetCommunitiesMatchingUuidsVo {

    private List<String> uuidList;
}
