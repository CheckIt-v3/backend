package com.techeer.checkIt.domain.reading.controller;

import com.techeer.checkIt.domain.book.dto.Response.BookRes;
import com.techeer.checkIt.domain.book.entity.Book;
import com.techeer.checkIt.domain.book.service.BookService;
import com.techeer.checkIt.domain.reading.dto.request.UpdateReadingStatusReq;
import com.techeer.checkIt.domain.reading.dto.response.UpdateReadingAndReadingVolumeRes;
import com.techeer.checkIt.domain.reading.entity.ReadingStatus;
import com.techeer.checkIt.domain.reading.dto.request.CreateReadingReq;
import com.techeer.checkIt.domain.reading.dto.request.UpdateReadingAndReadingVolumeReq;
import com.techeer.checkIt.domain.reading.service.ReadingService;
import com.techeer.checkIt.domain.user.entity.User;
import com.techeer.checkIt.domain.user.service.UserService;
import com.techeer.checkIt.global.result.ResultCode;
import com.techeer.checkIt.global.result.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "독서현황 API")
@RestController
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@RequestMapping("/api/v1/readings")
public class ReadingController {
    private final ReadingService readingService;
    private final UserService userService;
    private final BookService bookService;

    @ApiOperation(value = "내 서재 책 등록 API")
    @PostMapping("/{uid}")
    public ResponseEntity<ResultResponse> createReading(@PathVariable Long uid, @RequestBody CreateReadingReq createRequest) {
        User user = userService.findUserById(uid);
        Book book = bookService.findById(createRequest.getBookId());
        readingService.registerReading(user, book, createRequest);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.READING_CREATE_SUCCESS));
    }

    @ApiOperation(value = "상태 별 책 목록 API")
    @GetMapping("/{uid}")
    public ResponseEntity<List<BookRes>> getReadingByStatus(@PathVariable Long uid, @RequestParam(defaultValue = "") ReadingStatus status) {
        User user = userService.findUserById(uid);
        List<BookRes> readingList = readingService.findReadingByStatus(user.getId(), status);
        return ResponseEntity.ok(readingList);
    }

    @ApiOperation(value = "마지막 페이지 갱신 API")
    @PutMapping("/{uid}")
    public ResponseEntity<ResultResponse> updateReadingAndReadingVolume(@PathVariable Long uid, @RequestBody UpdateReadingAndReadingVolumeReq body) {
        User user = userService.findUserById(uid);
        Book book = bookService.findById(body.getBookId());
        UpdateReadingAndReadingVolumeRes updateReadingAndReadingVolumeRes = readingService.updateReadingAndReadingVolume(user,book,body);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.READING_UPDATE_SUCCESS, updateReadingAndReadingVolumeRes));
    }

    @ApiOperation(value = "독서 상태 변경 API")
    @PutMapping("status/{uid}")
    public ResponseEntity<ResultResponse> updateReadingStatus(@PathVariable Long uid, @RequestParam(defaultValue = "") ReadingStatus status, @RequestBody UpdateReadingStatusReq updateStatus) {
        User user = userService.findUserById(uid);
        Book book = bookService.findById(updateStatus.getBookId());
        readingService.updateReadingStatus(user.getId(), book.getId(), status, updateStatus);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.READING_STATUS_UPDATE_SUCCESS));
    }

    @ApiOperation(value = "읽은 퍼센트 API")
    @GetMapping("/percentages/{uid}")
    public ResponseEntity<ResultResponse> getPercentage(@PathVariable Long uid, @RequestParam(defaultValue = "") Long bid) {
        User user = userService.findUserById(uid);
        Book book = bookService.findById(bid);
        double percentage = readingService.findReadingByUserAndBook(user, book);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.READING_PERCENTAGE_SUCCESS,percentage));
    }
}
