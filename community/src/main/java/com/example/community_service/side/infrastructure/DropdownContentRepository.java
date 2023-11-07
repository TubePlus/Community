package com.example.community_service.side.infrastructure;

import com.example.community_service.side.domain.DropdownContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DropdownContentRepository extends JpaRepository<DropdownContent, Long> {
    List<DropdownContent> findAllByCommunitySideIdOrderByToggleOrder(Long id);
}
