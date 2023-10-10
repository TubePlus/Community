package com.example.user_service.user.infrastructure;


import com.example.user_service.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
