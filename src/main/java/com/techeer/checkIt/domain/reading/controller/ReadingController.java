package com.techeer.checkIt.domain.reading.controller;

import com.techeer.checkIt.domain.book.dto.Response.BookRes;
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
    public ResponseEntity<UpdateReadingAndReadingVolumeRes> updateReadingAndReadingVolume(@PathVariable Long uid, @RequestBody UpdateReadingAndReadingVolumeReq updateRequest) {
        User user = userService.findUserById(uid);
        Book book = bookService.findById(updateRequest.getBookId());
        ReadingVolume readingVolume = readingService.updateReadingAndReadingVolume(user,book,updateRequest);
        return ResponseEntity.ok(UpdateReadingAndReadingVolumeRes.of(updateRequest,readingVolume));
    }

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
}
