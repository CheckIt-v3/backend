package com.techeer.checkIt.domain.reading.entity;

import com.techeer.checkIt.domain.book.entity.Book;
import com.techeer.checkIt.domain.user.entity.User;
import com.techeer.checkIt.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Table(name = "READINGS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reading extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reading_id")
    private Long id;
    @Column(name = "date")
    private LocalDateTime date;
    @Column(name = "last_page")
    private int lastPage;
    private ReadingStatus status; // 책의 상태 [UNREAD,READING,READ]

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Builder
    public Reading(User user, Book book, LocalDateTime date, int lastPage, ReadingStatus status) {
        this.user = user;
        this.book = book;
        this.date = date;
        this.lastPage = lastPage;
        this.status = status;
    }
}
