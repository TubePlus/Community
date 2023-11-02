package com.example.community_service.community.dto.response;

import com.example.community_service.community.domain.CommunityManager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseGetManagerListDto {

    List<CommunityManager> managerList;
}
