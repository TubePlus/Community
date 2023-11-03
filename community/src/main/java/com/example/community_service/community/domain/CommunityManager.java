package com.example.community_service.community.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CommunityManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 매니저 uuid
    @Column(nullable = false, name = "manager_uuid")
    private String managerUuid;

    // 커뮤니티 id
    @Column(nullable = false, name = "community_id")
    private Long communityId;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDate updatedDate;
}
