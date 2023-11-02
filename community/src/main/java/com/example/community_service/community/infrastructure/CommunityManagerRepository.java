package com.example.community_service.community.infrastructure;

import com.example.community_service.community.domain.CommunityManager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommunityManagerRepository extends JpaRepository<CommunityManager, Long> {

    Boolean existsByCommunityIdAndManagerUuid(Long communityId, String managerUuid);
    Optional<CommunityManager> findByCommunityIdAndManagerUuid(Long communityId, String managerUuid);
    List<CommunityManager> findAllByCommunityId(Long communityId);
}
