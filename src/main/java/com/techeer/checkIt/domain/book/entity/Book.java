package com.techeer.checkIt.domain.book.entity;

import com.techeer.checkIt.domain.reading.entity.Reading;
import com.techeer.checkIt.domain.review.entity.Review;
import com.techeer.checkIt.entity.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "BOOKS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name = "publisher")
    private String publisher;
    @Column(name = "cover_image_url")
    private String coverImageUrl;
    @Column(name = "pages")
    private int pages;
    @Column(name = "height")
    private int height;
    @Column(name = "width")
    private int width;
    @Column(name = "thickness")
    private int thickness;
    @Column(name = "category", length = 512)
    private String category;
    @OneToMany(mappedBy = "book")
    private List<Reading> readings = new ArrayList<>();
    @OneToMany(mappedBy = "book")
    private List<Review> reviews = new ArrayList<>();
    @Column(name = "like_count")
    private Integer likeCount = 0;
    @Builder
    public Book(String title, String author, String publisher, String coverImageUrl, int pages, int height, int width, int thickness, String category) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.coverImageUrl = coverImageUrl;
        this.pages = pages;
        this.height = height;
        this.width = width;
        this.thickness = thickness;
        this.category = category;
    }

    @PostLoad
    @PrePersist
    private void initializeDefaults() {
        if (likeCount == null) {
            likeCount = 0; // 기본값을 0으로 설정
        }
    }
    public void updateLike(int like) {
        this.likeCount = like;
    }
    public void setLikeCount(Integer likeCount) { this.likeCount = likeCount; }
}
