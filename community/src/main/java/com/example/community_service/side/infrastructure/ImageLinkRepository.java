package com.example.community_service.side.infrastructure;

import com.example.community_service.side.domain.ImageLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageLinkRepository extends JpaRepository<ImageLink, Long> {
    ImageLink findByCommunitySideId(Long id);
}
