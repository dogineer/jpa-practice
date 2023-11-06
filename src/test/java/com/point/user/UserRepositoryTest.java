package com.point.user;

import jakarta.transaction.Transactional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("유저 회원가입 테스트")
    @Transactional
    public void joinUser() {
        User insertUser = userRepository.save(User.builder()
            .id(UUID.randomUUID())
            .name("테스트")
            .build());

        User saveUser = userRepository.findByName(insertUser.getName());

        Assertions.assertThat(insertUser).isEqualTo(saveUser);
    }
}
