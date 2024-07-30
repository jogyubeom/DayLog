package com.daylog.album.domain;

import com.daylog.couple.domain.Couple;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "albums")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "couple_id")
    private Couple couple;

    @Column
    private String imagePath;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column
    private Date uploadedAt;
}
