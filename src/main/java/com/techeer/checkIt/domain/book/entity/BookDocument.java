package com.techeer.checkIt.domain.book.entity;

import com.techeer.checkIt.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;

@Document(indexName = "book")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookDocument extends BaseEntity {
    @Id
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private String coverImageUrl;
    private int pages;
    private String category;

    @Builder
    public BookDocument(Long id, String title, String author, String publisher, String coverImageUrl, int pages, String category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.coverImageUrl = coverImageUrl;
        this.pages = pages;
        this.category = category;
    }
}