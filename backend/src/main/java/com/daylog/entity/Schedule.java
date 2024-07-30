package com.daylog.entity;

import com.daylog.domain.security.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "schedules")
@Getter
@Setter
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Couple couple;

    @ManyToOne
    private User user;

    @Column(length = 20, nullable = false)
    private String title;

    @Column(length = 255, nullable = false)
    private String content;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false, updatable = true)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false, updatable = true)
    private Date endDate;

}