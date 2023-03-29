package com.techeer.checkIt.domain.user.controller;

import com.techeer.checkIt.domain.book.entity.Book;
import com.techeer.checkIt.domain.book.service.BookService;
import com.techeer.checkIt.domain.reading.service.ReadingService;
import com.techeer.checkIt.domain.user.dto.request.UpdateReadingRequestDto;
import com.techeer.checkIt.domain.user.entity.User;
import com.techeer.checkIt.domain.user.service.UserService;
import io.swagger.annotations.Api;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@Api(tags = "회원 API")
@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final ReadingService readingService;
    private final BookService bookService;

    @PutMapping("/readings/{uid}")
    public void updateReading(@PathVariable Long uid, @RequestBody UpdateReadingRequestDto body) {
        LocalDate date = LocalDate.now();
        User user = userService.findUserById(uid);
        Book book = bookService.findById(body.getBookId());
        readingService.findReadingByDate(user,book,body);
    }
    @PostMapping
    public void signup() {
        userService.join();
    }
}
