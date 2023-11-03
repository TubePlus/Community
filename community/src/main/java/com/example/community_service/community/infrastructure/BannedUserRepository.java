package com.example.community_service.community.infrastructure;

import com.example.community_service.community.domain.BannedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BannedUserRepository extends JpaRepository<BannedUser, Long> {

    Boolean existsByCommunityIdAndBannedUuid(Long communityId, String bannedUuid);
    Optional<BannedUser> findByCommunityIdAndBannedUuid(Long communityId, String bannedUuid);
    List<BannedUser> findAllByCommunityId(Long communityId);
}
