package com.example.community_service.community.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "owner_uuid")
    private String ownerUuid;

    @Column(name ="banner_image")
    private String bannerImage;

    @Column(name="profile_image")
    private String profileImage;

    @Column(nullable = false, name = "community_name")
    private String communityName;

    @Column(name = "description")
    private String description;

    @Column(name = "youtube_name")
    private String youtubeName;

    @Column(nullable = false, name = "community_size")
    private Integer communitySize;

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
