package com.example.community_service.side.vo.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetDropdownResponse {
    private Long dropdownContentId;
    private String toggleTitle;
    private String toggleContent;
    private Integer toggleOrder;
}
