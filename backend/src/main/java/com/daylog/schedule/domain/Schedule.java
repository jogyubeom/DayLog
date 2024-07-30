package com.daylog.schedule.domain;

import com.daylog.couple.domain.Couple;
import com.daylog.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "schedules")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "couple_id")
    private Couple couple;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 20, nullable = false)
    private String title;

    @Column(length = 255, nullable = false)
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = true)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = true)
    private Date endDate;
}