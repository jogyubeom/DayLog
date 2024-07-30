package com.daylog.bookmark.domain;

import com.daylog.clip.domain.Clip;
import com.daylog.couple.domain.Couple;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bookmarks")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Bookmark {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "couple_id")
    private Couple couple;

    @ManyToOne
    @JoinColumn(name = "clip_id")
    private Clip clip;
}
