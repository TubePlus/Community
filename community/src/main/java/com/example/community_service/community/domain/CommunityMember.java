package com.example.community_service.community.domain;

import com.example.community_service.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommunityMember extends BaseEntity { // todo: 이름 변경(복수)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 가입한 커뮤니티 id
    @Column(nullable = false, name = "community_id")
    private Long communityId;

    // 가입한 유저 uuid
    @Column(nullable = false, name = "user_uuid")
    private String userUuid;

    // 밴 여부
    @Column(nullable = false, name = "is_banned")
    private Boolean isBanned;

    // todo: 밴 종료일만 넣을 것인가 아니면 밴 여부도 넣을 것인가 결정.
    // 밴 종료일
    @Column(name = "ban_end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime banEndDate;

    // 매니저 여부
    @Column(nullable = false, name = "is_manager")
    private Boolean isManager;

    // 멤버십 여부
    @Column(nullable = false, name = "is_membership_user")
    private Boolean isMembershipUser;

    // 탈퇴여부
    @Column(nullable = false, name = "is_active")
    private Boolean isActive;

    // 유저 커뮤니티 가입
    public static CommunityMember joinCommunity(
            Long communityId, String userUuid, Boolean isBanned,
            Boolean isManager, Boolean isMembershipUser, Boolean isActive) {

        return CommunityMember.builder()
                .communityId(communityId)
                .userUuid(userUuid)
                .isBanned(isBanned)
                .isManager(isManager)
                .isMembershipUser(isMembershipUser)
                .isActive(isActive)
                .build();
    }

    // 유저 커뮤니티 탈퇴(비활성화 처리)
    public void leaveCommunity() {

        this.isActive = false;
    }

    // 유저 커뮤니티 재가입(활성화 처리)
    public void rejoinCommunity() {

        this.isActive = true;
    }

    // 유저 밴 처리
    public void banUser(LocalDateTime banEndDate) {
        this.isBanned = true;
        this.isManager = false; // 매니저 권한도 같이 삭제된다.
        this.banEndDate = banEndDate;
    }

    // 유저 밴 종료일 수정
    public void updateBanEndDate(LocalDateTime banEndDate) {
        this.banEndDate = banEndDate;
    }

    // 유저 밴 해제 처리
    public void unbanUser() {
        this.isBanned = false;
    }

    // 유저 매니저 권한 부여
    public void grantManagerAuthority() {
        this.isManager = true;
    }

    // 유저 매니저 권한 해제
    public void takeManagerAuthority() {
        this.isManager = false;
    }
}
