package com.techeer.checkIt.fixture;

import static com.techeer.checkIt.fixture.BookFixtures.TEST_BOOK_ENT;
import static com.techeer.checkIt.fixture.UserFixtures.TEST_USER;

import com.techeer.checkIt.domain.review.dto.request.CreateReviewReq;
import com.techeer.checkIt.domain.review.dto.response.ReviewRes;
import com.techeer.checkIt.domain.review.entity.Review;

public class ReviewFixtures {
  public static final CreateReviewReq TEST_REVIEW_REQ =
      CreateReviewReq.builder()
          .bookId(2L)
          .title("title1")
          .content("content1")
          .grade(4.0)
          .build();

  public static final CreateReviewReq TEST_CHANGE_REVIEW_REQ =
      CreateReviewReq.builder()
          .bookId(2L)
          .title("title2")
          .content("content2")
          .grade(4.2)
          .build();

  public static final ReviewRes TEST_REVIEW_RES =
      ReviewRes.builder()
          .title("title1")
          .content("content1")
          .grade(4.0)
          .build();

  public static final ReviewRes TEST_CHANGE_REVIEW_RES =
      ReviewRes.builder()
          .title("title2")
          .content("content2")
          .grade(4.2)
          .build();

  public static final Review TEST_REVIEW =
      Review.builder()
          .user(TEST_USER)
          .book(TEST_BOOK_ENT)
          .title("title1")
          .contents("content1")
          .grade(4.0)
          .build();

  public static final Review TEST_REVIEW2 =
      Review.builder()
          .user(TEST_USER)
          .book(TEST_BOOK_ENT)
          .title("title1")
          .contents("content1")
          .grade(4.0)
          .build();

  public static final Review TEST_CHANGE_BEFOR_REVIEW =
      Review.builder()
          .user(TEST_USER)
          .book(TEST_BOOK_ENT)
          .title("title1")
          .contents("content1")
          .grade(4.0)
          .build();
}
