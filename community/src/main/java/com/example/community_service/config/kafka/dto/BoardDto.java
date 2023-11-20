package com.example.community_service.config.kafka.dto;

import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class BoardDto {
    private Long id;
    private Long communityId;
    private String boardName;
    private String boardType;
    private String boardDescription;
    private boolean visible;
    private boolean softDelete;

    private LocalDateTime limitDateTime;
}
