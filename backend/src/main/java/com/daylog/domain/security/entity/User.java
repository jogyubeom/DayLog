package com.daylog.domain.security.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 20, nullable = false, unique = true)
    private String email;

    @Column(length = 255, nullable = false)
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



    private static String generateRandomCode() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    private static String generateRandomImagePath() {
        return "images/" + UUID.randomUUID().toString() + ".jpg";
    }

    //현재 플랫폼에서는 권한 필요 없음!
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
