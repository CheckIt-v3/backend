package com.techeer.checkIt.domain.review.entity;

import com.techeer.checkIt.domain.book.entity.Book;
import com.techeer.checkIt.domain.user.entity.User;
import com.techeer.checkIt.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "REVIEWS")
public class Review extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(nullable = false, length = 255)
    private String contents;

    @Column(columnDefinition = "double default 0")
    private Double grade;

    @Builder
    public Review(User user, Book book, String contents, Double grade) {
        this.user = user;
        this.book = book;
        this.contents = contents;
        this.grade = grade;
    }
}
