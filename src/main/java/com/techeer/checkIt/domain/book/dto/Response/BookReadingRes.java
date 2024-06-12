package com.techeer.checkIt.domain.book.dto.Response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.querydsl.core.annotations.QueryProjection;
import com.techeer.checkIt.domain.book.entity.Book;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BookReadingRes {
  private Long id;
  private String title;
  private String author;
  private String publisher;
  private String coverImageUrl;
  private int height;
  private int width;
  private int pages;
  private int likes;
  private int lastPage;
  private Double percentage;
  private int grade;

  @QueryProjection
  public BookReadingRes(Book book, int grade) {
    this.id = book.getId();
    this.title = book.getTitle();
    this.author = book.getAuthor();
    this.publisher = book.getPublisher();
    this.coverImageUrl = book.getCoverImageUrl();
    this.height = book.getHeight();
    this.width = book.getWidth();
    this.pages = book.getPages();
    this.likes = book.getLikeCount();
    this.lastPage = book.getPages();
    this.percentage = 100.0;
    this.grade = grade;;
  }
}
