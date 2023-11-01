package com.example.community_service.community.infrastructure;

import com.example.community_service.community.domain.BannedUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BannedUserRepository extends JpaRepository<BannedUser, Long> {

    Boolean existsByCommunityIdAndBannedUuid(Long communityId, String bannedUuid);
    Optional<BannedUser> findByCommunityIdAndBannedUuid(Long communityId, String bannedUuid);
    List<BannedUser> findAllByCommunityId(Long communityId);
}
