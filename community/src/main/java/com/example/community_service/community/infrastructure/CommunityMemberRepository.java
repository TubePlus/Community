package com.example.community_service.community.infrastructure;

import com.example.community_service.community.domain.CommunityMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommunityMemberRepository extends JpaRepository<CommunityMember, Long> {

    Optional<CommunityMember> findByCommunityIdAndUserUuid(Long communityId, String userUuid);
    List<CommunityMember> findAllByCommunityId(Long communityId);
    Boolean existsByCommunityIdAndUserUuid(Long communityId, String userUuid);
}