package com.point.auth.userinfo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    private UUID id;

    @NotBlank(message = "name은 필수 입력 항목입니다.")
    @Column(length = 10, nullable = false)
    private String name;

    @NotBlank(message = "providerId는 필수 입력 항목입니다.")
    @Column(length = 35, nullable = false)
    private String providerId;

    @NotBlank(message = "email는 필수 입력 항목입니다.")
    @Column(length = 255, nullable = false)
    private String email;

    @NotBlank(message = "loginId는 필수 입력 항목입니다.")
    @Column(length = 35, nullable = false)
    private String loginId;

    @Column(nullable = true)
    private LocalDateTime joinAt;
}
