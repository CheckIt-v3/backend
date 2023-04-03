package com.techeer.checkIt.domain.reading.controller;

import com.techeer.checkIt.domain.book.dto.Response.BookResponse;
import com.techeer.checkIt.domain.book.entity.Book;
import com.techeer.checkIt.domain.book.service.BookService;
import com.techeer.checkIt.domain.reading.entity.ReadingStatus;
import com.techeer.checkIt.domain.reading.dto.request.CreateReadingReq;
import com.techeer.checkIt.domain.reading.dto.request.UpdateReadingAndReadingVolumeReq;
import com.techeer.checkIt.domain.reading.dto.response.UpdateReadingAndReadingVolumeRes;
import com.techeer.checkIt.domain.reading.service.ReadingService;
import com.techeer.checkIt.domain.readingVolume.entity.ReadingVolume;
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

    @PutMapping("/readings/{uid}")
    public ResponseEntity<ResultResponse> updateReadingAndReadingVolume(@PathVariable Long uid, @RequestBody UpdateReadingAndReadingVolumeReq body) {
        User user = userService.findUserById(uid);
        Book book = bookService.findById(body.getBookId());
        ReadingVolume readingVolume = readingService.updateReadingAndReadingVolume(user,book,body);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.READING_UPDATE_SUCCESS, readingVolume));
    }

    @PostMapping("/{uid}")
    public ResponseEntity<ResultResponse> createReading(@PathVariable Long uid, @RequestBody CreateReadingReq readingDto) {
        User user = userService.findUserById(uid);
        Book book = bookService.findById(readingDto.getBookId());
        readingService.registerReading(user, book, readingDto);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.READING_CREATE_SUCCESS));
    }

    @ApiOperation(value = "상태 별 책 목록 API")
    @GetMapping("/{uid}")
    public ResponseEntity<List<BookResponse>> getReadingByStatus(@PathVariable Long uid, @RequestParam(defaultValue = "") ReadingStatus status) {
        User user = userService.findUserById(uid);
        List<BookResponse> readingList = readingService.findReadingByStatus(user.getId(), status);
        return ResponseEntity.ok(readingList);
    }
}
