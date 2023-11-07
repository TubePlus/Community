package com.example.community_service.side.vo.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetImageLinkResponse {
    private Long imageLinkId;
    private String imageUrl;
    private String link;
}
