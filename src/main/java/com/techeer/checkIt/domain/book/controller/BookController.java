package com.techeer.checkIt.domain.book.controller;

import com.techeer.checkIt.domain.book.dto.Response.BookResponse;
import com.techeer.checkIt.domain.book.entity.Book;
import com.techeer.checkIt.domain.book.service.BookService;
import com.techeer.checkIt.domain.review.entity.Review;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "책 API")
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@RestController
public class BookController {
    private final BookService bookService;

    @ApiOperation(value = "책 검색 API")
    @GetMapping("/search")
    public ResponseEntity<List<BookResponse>> searchTitle(@RequestParam(defaultValue = "") String title) {
        List<BookResponse> bookList = bookService.findBookByTitle(title);
        return ResponseEntity.ok(bookList);
    }
}
