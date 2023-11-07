package com.example.community_service.side.domain;

import com.example.community_service.global.base.CodeValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SideType implements CodeValue {
    IMAGE_LINK("I","image link"),
    DROPDOWN("D","dropdown");
    private final String code;
    private final String value;
    @Override
    public String getCode() {
        return code;
    }
    @Override
    public String getValue() {
        return value;
    }
}
