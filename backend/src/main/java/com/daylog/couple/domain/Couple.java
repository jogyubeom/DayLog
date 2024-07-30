package com.daylog.couple.domain;

import com.daylog.pet.domain.Pet;
import com.daylog.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "couples")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Couple {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user1_id", nullable = false)
    private User user1;

    @OneToOne
    @JoinColumn(name = "user2_id", nullable = false)
    private User user2;

    @Column(length = 255, nullable = false)
    private String backgroundImagePath;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(nullable = false, updatable = true)
    private Date relationshipStartDate;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "pet_id", nullable = false, unique = true)
    private Pet pet;
}