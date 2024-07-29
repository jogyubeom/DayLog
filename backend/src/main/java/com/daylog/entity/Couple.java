package com.daylog.entity;

import com.daylog.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "couples")
@Getter
@Setter
public class Couple {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    private User user1;

    @OneToOne
    private User user2;

    @Column(length = 255, nullable = false)
    private String imagePath;

    // 사귄 날을 뜻하는 거면 어노테이션&이름 바꿔야 함
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @OneToOne
    private Pet pet;

}