package com.point.point;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "used_points")
public class PointsOfUsed {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    private UUID id;

    @Column(name = "user_id" , nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private Integer point;

    @Column(name = "point_dt", nullable = true)
    private LocalDateTime pointDate;

    @Column(name = "use_dt", nullable = true)
    private LocalDateTime useDate;

    public String toString(){
        return "\n" +
            "ID: " +id + "\n"
            + "USER ID: " + userId + "\n"
            + "POINT: " + point + "\n"
            + "POINT_DT: " + pointDate + "\n"
            + "USE_DT: " + useDate + "\n";
    }
}
