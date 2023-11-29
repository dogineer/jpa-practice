package com.point.user.service;

import com.point.auth.userinfo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByName(String name);
    User findByProviderId(String providerId);
}
