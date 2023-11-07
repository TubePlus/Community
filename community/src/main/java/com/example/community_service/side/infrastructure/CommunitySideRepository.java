package com.example.community_service.side.infrastructure;

import com.example.community_service.side.domain.CommunitySide;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunitySideRepository extends JpaRepository<CommunitySide, Long> {
    void deleteByIdAndCommunityId(Long communitySideId, Long communityId);

    List<CommunitySide> findAllByCommunityIdOrderBySideOrder(Long communityId);
}
