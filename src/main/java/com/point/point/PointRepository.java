package com.point.point;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PointRepository extends JpaRepository<Point, UUID> {
    Point findPointById(UUID id);
    Point findPointByUserId(UUID userId);

    List<Point> findPointsByUserId(UUID userId);
}
