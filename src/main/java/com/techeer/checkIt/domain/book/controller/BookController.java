package com.techeer.checkIt.domain.book.controller;

import com.techeer.checkIt.domain.book.dto.Response.BookRes;
import com.techeer.checkIt.domain.book.dto.Response.BookSearchRes;
import com.techeer.checkIt.domain.book.service.BookService;
import com.techeer.checkIt.global.result.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.techeer.checkIt.global.result.ResultCode.GET_NEW_BOOK_SUCCESS;

@Api(tags = "책 API")
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@RestController
public class BookController {
    private final BookService bookService;

    @ApiOperation(value = "책 검색 API")
    @GetMapping("/search")
    public ResponseEntity<List<BookSearchRes>> searchTitle(@RequestParam(defaultValue = "") String title) {
        List<BookSearchRes> bookList = bookService.findBookByTitle(title);
        return ResponseEntity.ok(bookList);
    }

    @ApiOperation(value = "책 한 권 조회 API")
    @GetMapping("{bookId}")
    public ResponseEntity<BookRes> getBookById(@PathVariable Long bookId){
        BookRes bookResponse = bookService.findBookById(bookId);
        return ResponseEntity.ok(bookResponse);
    }

    @ApiOperation(value = "신규 도서 조회 API")
    @GetMapping("/new")
    public ResponseEntity<ResultResponse> getNewBooksList() {
        Page<BookSearchRes> books = bookService.sortedBooksByTime();
        return ResponseEntity.ok(ResultResponse.of(GET_NEW_BOOK_SUCCESS, books));
    }

}
