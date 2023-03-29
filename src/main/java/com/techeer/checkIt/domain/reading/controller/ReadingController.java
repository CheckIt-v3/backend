package com.techeer.checkIt.domain.reading.controller;

import com.techeer.checkIt.domain.book.entity.Book;
import com.techeer.checkIt.domain.book.service.BookService;
import com.techeer.checkIt.domain.reading.dto.request.CreateReadingRequest;
import com.techeer.checkIt.domain.reading.dto.response.ReadingResponse;
import com.techeer.checkIt.domain.reading.entity.ReadingStatus;
import com.techeer.checkIt.domain.reading.service.ReadingService;
import com.techeer.checkIt.domain.user.entity.User;
import com.techeer.checkIt.domain.user.service.UserService;
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

    @PostMapping("/{uid}")
    public void createReading(@PathVariable Long uid, @RequestBody CreateReadingRequest readingDto) {
        User user = userService.findUserById(uid);
        Book book = bookService.findById(readingDto.getBookId());
        readingService.registerReading(user, book, readingDto);
    }

    @ApiOperation(value = "상태 별 책 목록 API")
    @GetMapping("/{uid}")
    public ResponseEntity<List<ReadingResponse>> getReadingByStatus(@PathVariable Long uid, @RequestParam(defaultValue = "") ReadingStatus status) {
        User user = userService.findUserById(uid);
        List<ReadingResponse> readingList = readingService.findReadingByStatus(user.getId(), status);
        return ResponseEntity.ok(readingList);
    }
}
