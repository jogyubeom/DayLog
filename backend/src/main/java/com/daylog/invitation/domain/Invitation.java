package com.daylog.invitation.domain;

import com.daylog.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "invitations")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user1_id", nullable = false, unique = true)
    private User user1;

    @OneToOne
    @JoinColumn(name = "user2_id", nullable = false, unique = true)
    private User user2;
}