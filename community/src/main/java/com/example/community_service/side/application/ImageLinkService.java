package com.example.community_service.side.application;

import com.example.community_service.side.vo.request.ImageLinkRequest;

public interface ImageLinkService {
    void createImageData(ImageLinkRequest imageLink, Long communitySideId);

    void deleteImageData(Long imageLinkId);
}
