package com.example.community_service.side.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@AllArgsConstructor // 프록시때문에 만드는 것
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@Table(name = "dropdown_content")
public class DropdownContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "toggle_title", length = 100)
    private String toggleTitle;
    @Column(name = "toggle_content", columnDefinition = "TEXT")
    private String toggleContent;
    @Column(name = "toggle_order")
    private Integer toggleOrder;
    @Column(name = "community_side_id")
    private Long communitySideId;
}
