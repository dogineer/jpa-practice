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
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
public class PointRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PointRepository pointRepository;

    private UUID testUser() {
        User initUser = userRepository.save(User.builder()
            .id(UUID.randomUUID())
            .name("test")
            .build());

        User user = userRepository.findByName(initUser.getName());

        return user.getId();
    }

    @Test
    @DisplayName("유저 포인트 적립")
    @Transactional
    public void addPoint() {
        UUID userId = testUser();
        Date current = new Date();

        Point addPoint = pointRepository.save(Point.builder()
            .id(UUID.randomUUID())
            .userId(userId)
            .point(1000)
            .date(current)
            .build());

        Point dbPoint = pointRepository.findPointById(addPoint.getId());

        Assertions.assertThat(addPoint).isEqualTo(dbPoint);
    }

    @Test
    @DisplayName("유저 포인트 적립 확인")
    @Transactional
    public void findUserPoint() {
        UUID userId = testUser();
        Date currentDate = new Date();

        UUID addPointId = pointRepository.save(Point.builder()
            .id(UUID.randomUUID())
            .userId(userId)
            .point(1000)
            .date(currentDate)
            .build()).getId();

        Point dbPoint = pointRepository.findPointById(addPointId);
        Point userPoint = pointRepository.findPointByUserId(userId);

        Assertions.assertThat(dbPoint).isEqualTo(userPoint);
    }

    @Test
    @DisplayName("유저 전체 포인트 확인")
    @Transactional
    public void findUserPoints() {
        UUID userId = testUser();
        Date currentDate = new Date();

        IntStream.range(0, 5).forEach(i -> {
            pointRepository.save(Point.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .point(1000)
                .date(currentDate)
                .build());
        });

        List<Point> userPoints = pointRepository.findPointsByUserId(userId);

        int userTotalPoint = userPoints.stream()
            .mapToInt(Point::getPoint)
            .sum();

        System.out.println("포인트 합계 : " + userTotalPoint);
    }
}
