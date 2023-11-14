package com.point.point;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;

    /*
    * 포인트 1000 적립
    * */
    public void addPoint(UUID userId){
        LocalDateTime date = LocalDateTime.now();

        pointRepository.save(Point.builder()
            .id(UUID.randomUUID())
            .userId(userId)
            .point(1000)
            .date(date)
            .build());
    }

    /*
    * 유저 포인트 리스트 확인
    * */
    public List<Point> findUserPoint(UUID userId){
        return pointRepository.findPointsByUserId(userId);
    }

    /*
    * 유저 포인트 사용
    * */
    public void removeUsePoint(Integer usePoint, UUID userId){
        List<Point> userTotalPoints = pointRepository.findPointsByUserId(userId);
        userTotalPoints.sort(Comparator.comparing(Point::getDate));

        int userTotalPoint = userTotalPoints.stream()
            .mapToInt(Point::getPoint)
            .sum();

        if (usePoint <= userTotalPoint) {
            for (Point point : userTotalPoints) {
                int pointValue = point.getPoint();

                if (usePoint >= pointValue) {
                    usePoint -= pointValue;

                    pointRepository.delete(point);
                } else {
                    return;
                }
            }
        } else {
            throw new RuntimeException("현재 유저가 갖고 있는 포인트 보다 사용할 포인트가 더 큽니다.");
        }
    }
}
