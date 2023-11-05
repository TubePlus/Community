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
@EntityListeners(AuditingEntityListener.class)
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 크리에이터 uuid
    @Column(nullable = false, name = "owner_uuid")
    private String ownerUuid;

    // 커뮤니티 배너 이미지
    @Column(name ="banner_image")
    private String bannerImage;

    // 커뮤니티 프로필 이미지
    @Column(name="profile_image")
    private String profileImage;

    // 커뮤니티 이름
    @Column(nullable = false, name = "community_name")
    private String communityName;

    // 커뮤니티 소개
    @Column(name = "description")
    private String description;

    // 커뮤니티 유튜브 채널 이름
    @Column(name = "youtube_name")
    private String youtubeName;

    // 커뮤니티 회원 수
    @Column(nullable = false, name = "community_size")
    private Integer communitySize;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDate updatedDate;

    public static Community createCommunity(
            String bannerImage, String profileImage, String youtubeName,
            String communityName, String description, String ownerUuid) {

        return Community.builder()
                .communitySize(1)
                .bannerImage(bannerImage)
                .profileImage(profileImage)
                .youtubeName(youtubeName)
                .communityName(communityName)
                .description(description)
                .ownerUuid(ownerUuid)
                .build();
    }
    public void updateCommunity(String communityName, String description, String profileImage, String bannerImage) {
        this.communityName = communityName;
        this.description = description;
        this.bannerImage = bannerImage;
        this.profileImage = profileImage;
    }

    public void increaseCommunitySize() {
        this.communitySize++;
    }

    public void decreaseCommunitySize() {
        this.communitySize--;
    }

}
