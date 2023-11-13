package com.point.point;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;

    /*
    * 포인트 1000 적립
    * */
    public void addPoint(UUID userId){
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = dateFormat.format(currentDate);

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

}
