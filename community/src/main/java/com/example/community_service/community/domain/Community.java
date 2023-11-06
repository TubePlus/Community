package com.example.community_service.community.domain;

import com.example.community_service.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Community extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // todo: 글자수 제한 채워넣기
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

    // todo: 변경됨
    // 커뮤니티 회원 수
    @Column(nullable = false, name = "community_member_count")
    private Integer communityMemberCount;

    // todo: communityMemberCount(1) 수정하기
    public static Community createCommunity(
            String bannerImage, String profileImage, String youtubeName,
            String communityName, String description, String ownerUuid) {

        return Community.builder()
                .communityMemberCount(1)
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

    // todo: 특정한 조건에 의해 변경되는 값이기 때문에 비즈니스 로직으로 빼기(도메인 로직 아님)
    public void increaseCommunitySize() {
        this.communityMemberCount++;
    }

    public void decreaseCommunitySize() {
        this.communityMemberCount--;
    }

    public void updateCommunityMemberCount(Integer communityMemberCount) {
        this.communityMemberCount = communityMemberCount;
    }
}
