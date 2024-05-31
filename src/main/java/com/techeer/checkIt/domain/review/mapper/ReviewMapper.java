package com.techeer.checkIt.domain.review.mapper;

import com.techeer.checkIt.domain.book.dto.Response.BookReadingRes;
import com.techeer.checkIt.domain.book.entity.Book;
import com.techeer.checkIt.domain.review.dto.response.ReviewRes;
import com.techeer.checkIt.domain.review.entity.Review;
import com.techeer.checkIt.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

  public Review toEntity(User user, Book book, String title, String contents, Double grade) {
    return Review.builder()
        .user(user)
        .book(book)
        .title(title)
        .contents(contents)
        .grade(grade)
        .build();
  }

  public ReviewRes toDto (Review review) {
    return ReviewRes.builder()
        .title(review.getTitle())
        .content(review.getContents())
        .grade(review.getGrade())
        .build();
  }

  public BookReadingRes toDtoByReview(Review review) {
    return BookReadingRes.builder()
            .id(review.getBook().getId())
            .title(review.getBook().getTitle())
            .author(review.getBook().getAuthor())
            .publisher(review.getBook().getPublisher())
            .coverImageUrl(review.getBook().getCoverImageUrl())
            .height(review.getBook().getHeight())
            .width(review.getBook().getWidth())
            .pages(review.getBook().getPages())
            .likes(review.getBook().getLikeCount())
            .lastPage(review.getBook().getPages())
            .percentage(100.0)
            .grade(review.getGrade())
            .build();
  }
}
