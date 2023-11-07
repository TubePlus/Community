package com.example.community_service.side.vo.request;

import com.example.community_service.side.domain.SideType;
import lombok.Getter;

import java.util.List;
@Getter
public class CommunitySideUpdateDropdownRequest {
    private Integer sideOrder;
    private SideType sideType;
    private String title;
    private List<DropdownContentUpdateRequest> dropdown;
}
