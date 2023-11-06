package com.point.point;

import com.point.user.User;
import com.point.user.UserRepository;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.UUID;

@SpringBootTest
public class PointRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PointRepository pointRepository;

    @Test
    @DisplayName("포인트 적립")
    @Transactional
    public void addPoint() {
        User findUser = userRepository.findByName("테스트");
        UUID userId = findUser.getId();
        Date current = new Date();

        Point addPoint = pointRepository.save(Point.builder()
            .id(UUID.randomUUID())
            .userId(userId)
            .point(1000)
            .date(current)
            .build());

        Point savePoint = pointRepository.findPointById(addPoint.getId());

        Assertions.assertThat(addPoint).isEqualTo(savePoint);
    }

    @Test
    @DisplayName("유저 포인트 확인")
    @Transactional
    public void findUserPoint() {
        User findUser = userRepository.findByName("테스트");
        UUID userId = findUser.getId();
        Date current = new Date();

        Point addPoint = pointRepository.save(Point.builder()
            .id(UUID.randomUUID())
            .userId(userId)
            .point(1000)
            .date(current)
            .build());

        Point savePoint = pointRepository.findPointById(addPoint.getId());

        UUID addPointUserId = addPoint.getUserId();
        UUID savePointUserId = savePoint.getUserId();

        if (addPointUserId == savePointUserId) {
            Point currentUserPoint = pointRepository.findPointByUserId(userId);

            System.out.println(addPointUserId);
            System.out.println(savePointUserId);
            System.out.println(currentUserPoint);
        }
    }
}
