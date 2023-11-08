package com.point.point;

import com.point.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class PointController {

    private final PointRepository pointRepository;
    private final UserRepository userRepository;

    @PostMapping("/point/add/{name}")
    public void addPoint(@PathVariable String name ){
        UUID userId = userRepository.findByName(name).getId();
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

    @GetMapping("/point/user/info/{name}")
    public List<Point> findUserPoint(@PathVariable  String name){
        UUID userId = userRepository.findByName(name).getId();

        return pointRepository.findPointsByUserId(userId);
    }
}
