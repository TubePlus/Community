package com.example.community_service.side.domain;

import jakarta.persistence.AttributeConverter;

import java.util.EnumSet;
import java.util.NoSuchElementException;

public class SideTypeConverter implements AttributeConverter<SideType, String>{
    @Override
    public String convertToDatabaseColumn(SideType sideType) {
        return sideType.getCode();
    }
    @Override
    public SideType convertToEntityAttribute(String dbData) {
        return EnumSet.allOf(SideType.class).stream()
                .filter(c -> c.getCode().equals(dbData))
                .findFirst()
                .orElseThrow(()-> new NoSuchElementException("존재하지 않는 코드입니다."));
    }
}
