package com.example.community_service.side.vo.response;

import com.example.community_service.side.domain.SideType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class GetCommunitySideResponse {
    private Integer sideOrder;
    private SideType sideType;
    private String title;
    private Long communityId;
    private List<GetDropdownResponse> dropdown;
    private GetImageLinkResponse imageLink;
}
