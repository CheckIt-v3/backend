package com.techeer.checkIt.domain.book.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Book {
    @Id @GeneratedValue
    @Column(name = "book_id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name = "publisher")
    private String publisher;
    @Column(name = "cover_image_url")
    private String cover_image_url;
    @Column(name = "pages")
    private int pages;
    @Column(name = "height")
    private int height;
    @Column(name = "width")
    private int width;
    @Column(name = "thickness")
    private int thickness;
    @Column(name = "category")
    private String category;
    @Column(name = "status")
    private String status;

    @Builder
    public Book(String title, String author, String publisher, String cover_image_url, int pages, int height, int width, int thickness, String category) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.cover_image_url = cover_image_url;
        this.pages = pages;
        this.height = height;
        this.width = width;
        this.thickness = thickness;
        this.category = category;
    }
    public void updateBookStatus(String status) {
        this.status = status;
    }
}
