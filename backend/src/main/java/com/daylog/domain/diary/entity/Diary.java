package com.daylog.domain.diary.entity;

import com.daylog.domain.security.entity.User;
import com.daylog.entity.Couple;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "diaries")
@Getter
@Setter
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "couple_id")
    private Couple couple;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(length = 255, nullable = false)
    private String imagePath;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = true)
    private Date createdAt;
}