package com.example.community_service.side.vo.request;

import lombok.Getter;

@Getter
public class DropdownContentUpdateRequest {
    private Long toggleId;
    private String toggleTitle;
    private String toggleContent;
    private Integer toggleOrder;
}
