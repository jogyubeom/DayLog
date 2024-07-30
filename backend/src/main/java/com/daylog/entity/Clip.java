package com.daylog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "clips")
@Getter
@Setter
public class Clip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Couple couple;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @Column(length = 20, nullable = false)
    private String title = "clip";

    @Column(nullable = false)
    private double score;

    @Column(length = 255, nullable = false)
    private String clipPath;
}