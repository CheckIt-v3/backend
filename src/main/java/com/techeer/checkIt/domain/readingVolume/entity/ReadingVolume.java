package com.techeer.checkIt.domain.readingVolume.entity;

import com.techeer.checkIt.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Table(name = "READING_VOLUMES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReadingVolume {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reading_volume_id")
    private Long id;
    @Column(name = "today_pages")
    private int todayPages;
    @Column(name = "date")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public ReadingVolume(int todayPages, LocalDate date, User user) {
        this.todayPages = todayPages;
        this.date = date;
        this.user = user;
    }

    public void sumTodayPages(int todayPages){
        this.todayPages += todayPages;
    }
}
