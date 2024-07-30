package com.daylog.user.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 25, nullable = false, unique = true)
    private String email;

    @Column(length = 60, nullable = false)
    private String hashedPassword;

    @Column(length = 10, nullable = false)
    private String name;

    @Column(length = 12, nullable = false, unique = true)
    private String tel;

    @Column(length = 6, nullable = false)
    private String nickname;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @Column(length = 255, nullable = false)
    private String profileImagePath;

    @Column(length = 8, nullable = false, unique = true)
    private String coupleCode;

    @Column(nullable = false)
    private Date birthDate;
}
