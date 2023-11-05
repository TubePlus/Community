package com.example.community_service.community.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class CommunityMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 가입한 커뮤니티 id
    @Column(nullable = false, name = "community_id")
    private Long communityId;

    // 가입한 유저 uuid
    @Column(nullable = false, name = "user_uuid")
    private String userUuid;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    public static CommunityMember joinCommunity(Long communityId, String userUuid) {
        return CommunityMember.builder()
                .communityId(communityId)
                .userUuid(userUuid)
                .build();
    }
}
