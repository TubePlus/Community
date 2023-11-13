package com.example.community_service.community.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommunityManager { // todo: communityMember와 합치는 방안 고려?

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 매니저 uuid
    @Column(nullable = false, name = "manager_uuid")
    private String managerUuid;

    // 커뮤니티 id
    @Column(nullable = false, name = "community_id")
    private Long communityId;

    public static CommunityManager createManager(String managerUuid, Long communityId) {

        return CommunityManager.builder()
                .managerUuid(managerUuid)
                .communityId(communityId)
                .build();
    }
}