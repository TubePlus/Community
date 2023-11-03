package com.example.community_service.community.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BannedUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 밴 당한 커뮤니티의 id
    @Column(nullable = false, name = "community_id")
    private Long communityId;

    // 밴 당한 유저의 uuid
    @Column(nullable = false, name = "banned_uuid")
    private String bannedUuid;

    // 밴 종료일
    @Column(nullable = false, name = "ban_end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime banEndDate;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDate updatedDate;

    public void updateBanEndDate(LocalDateTime banEndDate) {
        this.banEndDate = banEndDate;
    }
}
