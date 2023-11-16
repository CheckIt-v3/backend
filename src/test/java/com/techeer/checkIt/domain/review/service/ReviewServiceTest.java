package com.techeer.checkIt.domain.review.service;

import static com.techeer.checkIt.fixture.ReviewFixtures.TEST_CHANGE_BEFOR_REVIEW;
import static com.techeer.checkIt.fixture.ReviewFixtures.TEST_CHANGE_REVIEW_REQ;
import static com.techeer.checkIt.fixture.ReviewFixtures.TEST_CHANGE_REVIEW_RES;
import static com.techeer.checkIt.fixture.ReviewFixtures.TEST_REVIEW;
import static com.techeer.checkIt.fixture.ReviewFixtures.TEST_REVIEW2;
import static com.techeer.checkIt.fixture.ReviewFixtures.TEST_REVIEW_RES;
import static com.techeer.checkIt.fixture.UserFixtures.TEST_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import com.techeer.checkIt.domain.review.dto.response.ReviewRes;
import com.techeer.checkIt.domain.review.mapper.ReviewMapper;
import com.techeer.checkIt.domain.review.repository.ReviewRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

  @InjectMocks
  private ReviewService reviewService;
  @Mock
  private ReviewRepository reviewRepository;
  @Mock
  private ReviewMapper reviewMapper;

  @Test
  @DisplayName("Service) review 조회")
  void findReviewByUserNameIdBookId() {
    // given
    given(reviewRepository.findByUserNameBookId(any(), eq(2L))).willReturn(
        Optional.ofNullable(TEST_REVIEW2));
    when(reviewMapper.toDto(TEST_REVIEW2)).thenReturn(TEST_REVIEW_RES);

    // when
    ReviewRes reviewRes = reviewService.findReviewByUserNameIdBookId(TEST_USER, 2L);

    // then
    assertThat(reviewRes.getTitle()).isEqualTo(TEST_REVIEW2.getTitle());
  }

  @Test
  @DisplayName("Service) review 업데이트 실패")
  void updateReviewFailure() {
    // given
    given(reviewRepository.findByUserNameBookId(any(), eq(2L))).willReturn(
        Optional.ofNullable(TEST_REVIEW));
    when(reviewMapper.toDto(TEST_REVIEW)).thenReturn(TEST_CHANGE_REVIEW_RES);

    // when
    ReviewRes reviewRes = reviewService.updateReview(TEST_USER, TEST_CHANGE_REVIEW_REQ);

    // then
    assertThat(reviewRes.getTitle()).isNotEqualTo(TEST_CHANGE_BEFOR_REVIEW.getTitle());
  }

  @Test
  @DisplayName("Service) review 업데이트 성공")
  void updateReviewSuccess() {
    // given
    given(reviewRepository.findByUserNameBookId(any(), eq(2L))).willReturn(
        Optional.ofNullable(TEST_REVIEW));
    when(reviewMapper.toDto(TEST_REVIEW)).thenReturn(TEST_CHANGE_REVIEW_RES);

    // when
    ReviewRes reviewRes = reviewService.updateReview(TEST_USER, TEST_CHANGE_REVIEW_REQ);

    // then
    assertThat(reviewRes.getTitle()).isEqualTo(TEST_CHANGE_REVIEW_RES.getTitle());
  }
}
