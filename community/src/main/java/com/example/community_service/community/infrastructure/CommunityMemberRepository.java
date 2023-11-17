package com.example.community_service.community.infrastructure;

import com.example.community_service.community.domain.CommunityMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityMemberRepository extends JpaRepository<CommunityMember, Long> {

    Optional<CommunityMember> findByCommunityIdAndUserUuid(Long communityId, String userUuid);
    List<CommunityMember> findAllByCommunityId(Long communityId);
    Boolean existsByCommunityIdAndUserUuid(Long communityId, String userUuid);
    Long countByCommunityIdAndAndIsActiveTrue(Long communityId);
    Boolean existsByCommunityIdAndUserUuidAndIsActiveTrue(Long communityId, String userUuid);
}