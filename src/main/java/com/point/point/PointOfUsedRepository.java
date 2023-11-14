package com.point.point;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PointOfUsedRepository extends JpaRepository<PointsOfUsed, UUID> {
    List<PointsOfUsed> findPointsOfUsedByUserId(UUID userId);
}
