package com.daylog.domain.user.entity;

import com.daylog.domain.user.dto.CustomUserInfoDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "users")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 20, nullable = false, unique = true)
    private String email;

    @Column(length = 20, nullable = false)
    private String password;

    @Column(length = 10, nullable = false)
    private String name;

    @Column(length = 12, nullable = false, unique = true)
    private String tel;

    @Column(length = 10, nullable = false)
    private String nickname;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @Column(length = 255, nullable = false)
    private String imagePath;

    @Column(length = 8, nullable = false, unique = true)
    private String coupleCode;

    @Temporal(TemporalType.DATE)
    @Column(nullable = true, updatable = false)
    private String birthDate;
    public CustomUserInfoDto toCustomUserInfoDto() {
        return CustomUserInfoDto.builder()
                .id(this.id)
                .email(this.email)
                .name(this.name)
                .password(this.password)
                .coupleCode(this.coupleCode)
                .build();
    }
}
