package com.point.point;

import com.point.user.User;
import com.point.user.UserRepository;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;

@SpringBootTest
public class PointsOfUsedRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PointRepository pointRepository;

    @Autowired
    PointOfUsedRepository pointOfUsedRepository;

    private UUID testUser() {
        User initUser = userRepository.save(User.builder()
            .id(UUID.randomUUID())
            .name("test")
            .build());

        User user = userRepository.findByName(initUser.getName());

        return user.getId();
    }

    LocalDateTime date = LocalDateTime.now();

    @Test
    @DisplayName("유저 포인트 사용 내역 조회")
    @Transactional
    public void userPointsUseDetails() {
        UUID userId = testUser();

        List<Point> addPoints = new ArrayList<>(IntStream.range(0, 5)
            .mapToObj(i -> Point.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .point(1000)
                .date(date.plusSeconds(i))
                .build())
            .toList());

        pointRepository.saveAll(addPoints);
        addPoints.sort(Comparator.comparing(Point::getDate));

        List<Point> userTotalPoints = pointRepository.findPointsByUserId(userId);
        userTotalPoints.sort(Comparator.comparing(Point::getDate));

        Assertions.assertThat(addPoints)
            .usingRecursiveComparison()
            .isEqualTo(userTotalPoints);

        int userTotalPoint = userTotalPoints.stream()
            .mapToInt(Point::getPoint)
            .sum();

        int usePoint = 3000;

        if (usePoint <= userTotalPoint) {
            List<PointsOfUsed> pointsOfUsed = new ArrayList<>();
            int testCount = userTotalPoints.size();

            for (Point point : userTotalPoints) {
                int pointValue = point.getPoint();

                if (usePoint >= pointValue) {
                    usePoint -= pointValue;

                    PointsOfUsed updatePointsOfUsed = PointsOfUsed.builder()
                        .id(point.getId())
                        .userId(point.getUserId())
                        .point(point.getPoint())
                        .pointDate(point.getDate())
                        .useDate(date.plusSeconds(testCount))
                        .build();

                    pointsOfUsed.add(updatePointsOfUsed);
                    pointOfUsedRepository.save(updatePointsOfUsed);
                } else {
                    System.out.println("DONE");
                }

                testCount--;
                System.out.println("count: " + testCount);
            }

            List<PointsOfUsed> resultPointDb = pointOfUsedRepository.findPointsOfUsedByUserId(userId);

            pointsOfUsed.sort(Comparator.comparing(PointsOfUsed::getUseDate));
            resultPointDb.sort(Comparator.comparing(PointsOfUsed::getUseDate));

            System.out.println("[사용한 포인트 엔티티]");
            System.out.println(pointsOfUsed);

            System.out.println("\n[리포지토리 유저 아이디로 포인트 조회 잘 들어갔는지 확인]");
            System.out.println(resultPointDb);

            Assertions.assertThat(pointsOfUsed)
                .usingRecursiveComparison()
                .isEqualTo(resultPointDb);
        } else {
            throw new RuntimeException("현재 유저가 갖고 있는 포인트 보다 사용할 포인트가 더 큽니다.");
        }
    }
}
