package com.example.community_service.community.infrastructure;

import com.example.community_service.community.domain.CommunityMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityMemberRepository extends JpaRepository<CommunityMember, Long> {

    Boolean existsByCommunityIdAndUserUuid(Long communityId, String userUuid);
}