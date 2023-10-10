package com.example.user_service.user.domain;

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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "email")
    private String email;

    @Column(nullable = false, name = "user_name")
    private String userName;

    @Column(nullable = false, name = "profile_image")
    private String profileImage;

    @Column(nullable = false, name = "soft_delete", columnDefinition = "int default 0")
    private Integer softDelete;

    @Column(nullable = false, name = "ban_delete", columnDefinition = "int default 0")
    private Integer banDelete;

    @Column(nullable = false, name = "google_auth")
    private String googleAuth;

    @Column(name = "info")
    private String info;
}
