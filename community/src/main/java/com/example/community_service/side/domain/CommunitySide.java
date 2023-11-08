package com.example.community_service.side.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@AllArgsConstructor // 프록시때문에 만드는 것
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@Table(name = "community_side")
public class CommunitySide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="side_order", nullable = false)
    private Integer sideOrder;
    @Column(name="side_type", nullable = false, length = 10)
    @Convert(converter = SideTypeConverter.class)
    private SideType sideType;
    @Column(name= "title", length = 100)
    private String title;
    @Column(name = "community_id",nullable = false)
    private Long communityId;
}
