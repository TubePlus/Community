package com.example.community_service.community.infrastructure;


import com.example.community_service.community.domain.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {

    Boolean existsByCommunityName(String communityName);
    Boolean existsByOwnerUuid(String ownerUuid);
}