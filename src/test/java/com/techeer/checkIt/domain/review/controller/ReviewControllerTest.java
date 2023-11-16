package com.techeer.checkIt.domain.review.controller;

import static com.techeer.checkIt.fixture.BookFixtures.TEST_BOOK_ENT;
import static com.techeer.checkIt.fixture.ReviewFixtures.TEST_CHANGE_REVIEW_REQ;
import static com.techeer.checkIt.fixture.ReviewFixtures.TEST_CHANGE_REVIEW_RES;
import static com.techeer.checkIt.fixture.ReviewFixtures.TEST_REVIEW;
import static com.techeer.checkIt.fixture.ReviewFixtures.TEST_REVIEW_REQ;
import static com.techeer.checkIt.fixture.ReviewFixtures.TEST_REVIEW_RES;
import static com.techeer.checkIt.fixture.UserFixtures.TEST_USER;
import static com.techeer.checkIt.fixture.UserFixtures.TEST_USER_DETAIL;
import static com.techeer.checkIt.global.result.ResultCode.GET_REVIEW_SUCCESS;
import static com.techeer.checkIt.global.result.ResultCode.REVIEW_CREATE_SUCCESS;
import static com.techeer.checkIt.global.result.ResultCode.REVIEW_DELETE_SUCCESS;
import static com.techeer.checkIt.global.result.ResultCode.REVIEW_UPDATE_SUCCESS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techeer.checkIt.domain.book.service.BookService;
import com.techeer.checkIt.domain.review.repository.ReviewRepository;
import com.techeer.checkIt.domain.review.service.ReviewService;
import com.techeer.checkIt.domain.user.service.UserService;
import com.techeer.checkIt.global.result.ResultResponse;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {

  @MockBean
  private ReviewService reviewService;
  @MockBean
  private UserService userService;
  @MockBean
  private BookService bookService;
  @MockBean
  private ReviewRepository reviewRepository;;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;


  private String toJsonString(Object object) throws JsonProcessingException {
    return objectMapper.writeValueAsString(object);
  }

  @Test
  @DisplayName("Controller) 리뷰 생성")
  void createReview() throws Exception {

    // when
    when(userService.findUserByUsername(any())).thenReturn(TEST_USER);
    when(bookService.findById(any())).thenReturn(TEST_BOOK_ENT);
    when(reviewService.findReviewByUserNameIdBookId(any(), any())).thenReturn(TEST_REVIEW_RES);

    // then
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/v1/reviews")
                .with(SecurityMockMvcRequestPostProcessors.csrf()) // CSRF 토큰을 요청에 추가
                .with(SecurityMockMvcRequestPostProcessors.user(TEST_USER_DETAIL)) // 현재 사용자 정보 전달
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString(TEST_REVIEW_REQ)))
        .andExpect(status().isOk())
        .andExpect(
            content().string(toJsonString(ResultResponse.of(REVIEW_CREATE_SUCCESS, TEST_REVIEW_RES))))
        .andDo(print());
  }

  @Test
  @DisplayName("Controller) 리뷰 조회 성공")
  void getReviewByUserNameBookIdSuccess() throws Exception {
    //when
    when(userService.findUserByUsername(any())).thenReturn(TEST_USER);
    when(reviewService.findReviewByUserNameIdBookId(any(), eq(2L))).thenReturn(TEST_REVIEW_RES);

    //then
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/api/v1/reviews/{bookId}", 2L)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .with(SecurityMockMvcRequestPostProcessors.user(TEST_USER_DETAIL))
                .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(
            content().string(toJsonString(ResultResponse.of(GET_REVIEW_SUCCESS, TEST_REVIEW_RES))))
        .andDo(print());
  }

  @Test
  @DisplayName("Controller) 리뷰 조회 실패")
  void getReviewByUserNameBookIdFailure() throws Exception {
    //when
    when(userService.findUserByUsername(any())).thenReturn(TEST_USER);
    when(reviewService.findReviewByUserNameIdBookId(any(), eq(2L))).thenReturn(TEST_REVIEW_RES);

    //then
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/api/v1/reviews/{bookId}", 3L)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .with(SecurityMockMvcRequestPostProcessors.user(TEST_USER_DETAIL))
                .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(
            content().string(toJsonString(ResultResponse.of(GET_REVIEW_SUCCESS, null))))
        .andDo(print());
  }

  @Test
  @DisplayName("Controller) 리뷰 삭제")
  void deleteReviewByUserNameBookId() throws Exception {
    //when
    when(userService.findUserByUsername(any())).thenReturn(TEST_USER);
    when(reviewRepository.findByUserNameBookId(any(), eq(2L))).thenReturn(
        Optional.ofNullable(TEST_REVIEW));

    //then
    mockMvc
        .perform(
            MockMvcRequestBuilders.delete("/api/v1/reviews/{bookId}", 2L)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .with(SecurityMockMvcRequestPostProcessors.user(TEST_USER_DETAIL))
                .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(
            content().string(toJsonString(ResultResponse.of(REVIEW_DELETE_SUCCESS))))
        .andDo(print());
  }

  @Test
  @DisplayName("Controller) 리뷰 내용 변경")
  void updateReview() throws Exception {
    //when
    when(userService.findUserByUsername(any())).thenReturn(TEST_USER);
    when(reviewRepository.findByUserNameBookId(any(), eq(2L))).thenReturn(
        Optional.ofNullable(TEST_REVIEW));
    when(reviewService.updateReview(any(), any())).thenReturn(TEST_CHANGE_REVIEW_RES);

    //then
    mockMvc
        .perform(
            MockMvcRequestBuilders.put("/api/v1/reviews")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .with(SecurityMockMvcRequestPostProcessors.user(TEST_USER_DETAIL))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString(TEST_CHANGE_REVIEW_REQ)))
        .andExpect(status().isOk())
        .andExpect(
            content().string(toJsonString(ResultResponse.of(REVIEW_UPDATE_SUCCESS, TEST_CHANGE_REVIEW_RES))))
        .andDo(print());
  }

}

