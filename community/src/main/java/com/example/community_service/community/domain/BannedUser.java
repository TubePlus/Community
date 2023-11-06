package com.example.community_service.community.domain;

import com.example.community_service.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BannedUser extends BaseEntity {

    // todo: 커뮤니티멤버와 합치는 방안 고려?
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

    public static BannedUser banUser (Long communityId, String bannedUuid, LocalDateTime banEndDate) {

        return BannedUser.builder()
                .communityId(communityId)
                .bannedUuid(bannedUuid)
                .banEndDate(banEndDate)
                .build();
    }

    public void updateBanEndDate(LocalDateTime banEndDate) {
        this.banEndDate = banEndDate;
    }
}
