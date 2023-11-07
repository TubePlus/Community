package com.example.community_service.side.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@AllArgsConstructor // 프록시때문에 만드는 것
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@Table(name = "image_link")
public class ImageLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "link")
    private String link;
    @Column(name = "community_side_id")
    private Long communitySideId;
}
