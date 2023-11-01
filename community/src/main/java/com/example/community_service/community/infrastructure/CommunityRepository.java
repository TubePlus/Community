package com.example.community_service.community.infrastructure;


import com.example.community_service.community.domain.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long> {

    Boolean existsByOwnerUuid(String ownerUuid);
}