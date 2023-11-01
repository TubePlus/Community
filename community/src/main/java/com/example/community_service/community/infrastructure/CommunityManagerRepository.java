package com.example.community_service.community.infrastructure;

import com.example.community_service.community.domain.CommunityManager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityManagerRepository extends JpaRepository<CommunityManager, Long> {

    Boolean existsByCommunityIdAndManagerUuid(Long communityId, String managerUuid);
}
