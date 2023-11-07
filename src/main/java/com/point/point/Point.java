package com.point.point;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;
import java.util.UUID;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "points")
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    private UUID id;

    @Column(name = "user_id" ,unique = true, nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private Integer point;

    @Column(nullable = true)
    private Date date;
}
