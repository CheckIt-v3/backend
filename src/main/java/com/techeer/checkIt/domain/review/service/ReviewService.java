package com.techeer.checkIt.domain.review.service;

import com.techeer.checkIt.domain.book.entity.Book;
import com.techeer.checkIt.domain.review.dto.request.CreateReviewReq;
import com.techeer.checkIt.domain.review.dto.response.ReviewRes;
import com.techeer.checkIt.domain.review.entity.Review;
import com.techeer.checkIt.domain.review.mapper.ReviewMapper;
import com.techeer.checkIt.domain.review.exception.ReviewNotFoundException;
import com.techeer.checkIt.domain.review.repository.ReviewRepository;
import com.techeer.checkIt.domain.user.entity.User;
import javax.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewService {
  private final ReviewRepository reviewRepository;
  private final ReviewMapper reviewMapper;

  public void createReview (User user, Book book, CreateReviewReq createReviewReq) {
    Review review = reviewMapper.toEntity(user, book, createReviewReq.getTitle(), createReviewReq.getContent(), createReviewReq.getGrade());
    reviewRepository.save(review);
  }

  public ReviewRes findReviewByUserNameIdBookId (User user, Long bookId) {
    Review review = reviewRepository.findByUserNameBookId(user.getNickname(), bookId).orElseThrow(ReviewNotFoundException::new);
    return reviewMapper.toDto(review);
  }

  public void deleteReview (User user, Long bookId) {
    Review review = reviewRepository.findByUserNameBookId(user.getNickname(), bookId).orElseThrow(ReviewNotFoundException::new);
    review.delete();
  }

  public ReviewRes updateReview (User user, CreateReviewReq createReviewReq) {
    Review review = reviewRepository.findByUserNameBookId(user.getNickname(), createReviewReq.getBookId()).orElseThrow(ReviewNotFoundException::new);
    review.updateReview(createReviewReq);
    return reviewMapper.toDto(review);
  }
}
