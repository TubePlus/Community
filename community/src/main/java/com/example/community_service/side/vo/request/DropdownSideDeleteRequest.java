package com.example.community_service.side.vo.request;

import lombok.Getter;

import java.util.List;

@Getter
public class DropdownSideDeleteRequest {
    private List<Long> dropdownContentId;
}
