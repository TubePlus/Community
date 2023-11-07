package com.example.community_service.side.application;

import com.example.community_service.side.domain.ImageLink;
import com.example.community_service.side.infrastructure.ImageLinkRepository;
import com.example.community_service.side.vo.request.ImageLinkRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageLinkServiceImpl implements ImageLinkService{
    private final ImageLinkRepository imageLinkRepository;
    // imageLink 만들기
    @Override
    public void createImageData(ImageLinkRequest imageLinkRequest, Long communitySideId) {
        ImageLink imageLink = ImageLink.builder()
                .imageUrl(imageLinkRequest.getImageUrl())
                .link(imageLinkRequest.getHyperlink())
                .communitySideId(communitySideId)
                .build();
        imageLinkRepository.save(imageLink);
    }
    // imageLink 삭제하기
    @Override
    public void deleteImageData(Long imageLinkId) {
        imageLinkRepository.deleteById(imageLinkId);
    }
}
