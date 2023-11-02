package com.example.community_service.community.vo.response;

import com.example.community_service.community.domain.CommunityManager;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseGetManagerListVo {

    private List<CommunityManager> managerList;
}
