package com.point.point;

import com.point.user.User;
import com.point.user.UserRepository;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.*;
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

    Date currentDate = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String date = dateFormat.format(currentDate);

    @Test
    @DisplayName("유저 포인트 적립")
    @Transactional
    public void addPoint() {
        UUID userId = testUser();

        Point addPoint = pointRepository.save(Point.builder()
            .id(UUID.randomUUID())
            .userId(userId)
            .point(1000)
            .date(date)
            .build());

        Point dbPoint = pointRepository.findPointById(addPoint.getId());

        Assertions.assertThat(addPoint).isEqualTo(dbPoint);
    }

    @Test
    @DisplayName("유저 포인트 적립 확인")
    @Transactional
    public void findUserPoint() {
        UUID userId = testUser();

        UUID addPointId = pointRepository.save(Point.builder()
            .id(UUID.randomUUID())
            .userId(userId)
            .point(1000)
            .date(date)
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

        List<Point> addPoints = new ArrayList<>(IntStream.range(0, 4)
            .mapToObj(i -> Point.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .point(1000)
                .build())
            .toList());

        pointRepository.saveAll(addPoints);

        List<Point> userPoints = pointRepository.findPointsByUserId(userId);

        userPoints.sort(Comparator.comparing(Point::getId));
        addPoints.sort(Comparator.comparing(Point::getId));

        Assertions.assertThat(addPoints)
            .usingRecursiveComparison()
            .isEqualTo(userPoints);
    }

    @Test
    @DisplayName("5000 포인트 가진 유저 3000 포인트 사용")
    @Transactional
    public void useUserPointCase1() {
        UUID userId = testUser();

        List<Point> addPoints = new ArrayList<>(IntStream.range(0, 5)
            .mapToObj(i -> Point.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .point(1000)
                .date(date + i)
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
        System.out.println("현재 가지고 있는 포인트 : " + userTotalPoint);

        int usePoint = 3000;
        System.out.println("사용할 포인트 : " + usePoint);

        if (usePoint <= userTotalPoint) {
            List<Point> updatedPoints = new ArrayList<>();

            for (Point point : userTotalPoints) {
                int pointValue = point.getPoint();

                if (usePoint >= pointValue) {
                    usePoint -= pointValue;
                } else {
                    updatedPoints.add(Point.builder()
                        .id(point.getId())
                        .userId(point.getUserId())
                        .point(pointValue - usePoint)
                        .date(point.getDate())
                        .build());
                }
            }

            userTotalPoints = updatedPoints;

            System.out.println(userTotalPoints);

            int userRemainPoint = userTotalPoints.stream()
                .mapToInt(Point::getPoint)
                .sum();

            System.out.println("남은 포인트 : " + userRemainPoint);
            Assertions.assertThat(userRemainPoint).isEqualTo(2000);
        } else {
            throw new RuntimeException("현재 유저가 갖고 있는 포인트 보다 사용할 포인트가 더 큽니다.");
        }
    }

    @Test
    @DisplayName("1000 포인트 가진 유저 3000 포인트 사용")
    @Transactional
    public void useUserPointCase2() {
        UUID userId = testUser();

        List<Point> addPoints = new ArrayList<>(IntStream.range(0, 1)
            .mapToObj(i -> Point.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .point(1000)
                .date(date + i)
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
        System.out.println("현재 가지고 있는 포인트 : " + userTotalPoint);

        int usePoint = 3000;
        System.out.println("사용할 포인트 : " + usePoint);

        if (usePoint <= userTotalPoint) {
            List<Point> updatedPoints = new ArrayList<>();

            for (Point point : userTotalPoints) {
                int pointValue = point.getPoint();

                if (usePoint >= pointValue) {
                    usePoint -= pointValue;
                } else {
                    updatedPoints.add(Point.builder()
                        .id(point.getId())
                        .userId(point.getUserId())
                        .point(pointValue - usePoint)
                        .date(point.getDate())
                        .build());
                }
            }

            userTotalPoints = updatedPoints;

            System.out.println(userTotalPoints);

            int userRemainPoint = userTotalPoints.stream()
                .mapToInt(Point::getPoint)
                .sum();

            System.out.println("남은 포인트 : " + userRemainPoint);
            Assertions.assertThat(userRemainPoint).isEqualTo(2000);
        } else {
            throw new RuntimeException("현재 유저가 갖고 있는 포인트 보다 사용할 포인트가 더 큽니다.");
        }
    }
}
