package com.example.community_service.community.infrastructure;


import com.example.community_service.community.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
