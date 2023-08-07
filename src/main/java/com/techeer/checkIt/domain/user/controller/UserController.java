package com.techeer.checkIt.domain.user.controller;

import com.techeer.checkIt.domain.user.dto.request.UserJoinReq;
import com.techeer.checkIt.domain.user.dto.request.UserLoginReq;
import com.techeer.checkIt.domain.user.exception.UserDuplicatedException;
import com.techeer.checkIt.domain.user.mapper.UserMapper;
import com.techeer.checkIt.domain.user.service.LoginService;
import com.techeer.checkIt.domain.user.service.UserService;
import com.techeer.checkIt.global.jwt.JwtToken;
import com.techeer.checkIt.global.result.ResultCode;
import com.techeer.checkIt.global.result.ResultResponse;
import io.swagger.annotations.Api;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import static com.techeer.checkIt.global.result.ResultCode.*;

@Api(tags = "회원 API")
@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final LoginService loginService;

    @PostMapping("/join")
    public ResponseEntity<ResultResponse> join(
            @RequestBody @Valid UserJoinReq userJoinReq) {
        if (userService.isDuplicatedUsername(userJoinReq.getUsername())) {
            throw new UserDuplicatedException();
        }
        userService.join(userJoinReq);
        return ResponseEntity.ok(ResultResponse.of(USER_REGISTRATION_SUCCESS));
    }

    @GetMapping("/duplicated/{username}")
    public ResponseEntity<ResultResponse> isDuplicatedUsername(@PathVariable String username) {
        boolean isDuplicated = userService.isDuplicatedUsername(username);

        if (isDuplicated) {
            return ResponseEntity.ok(ResultResponse.of(ResultCode.USER_USERNAME_DUPLICATED, true));
        }
        return ResponseEntity.ok(ResultResponse.of(USER_USERNAME_NOT_DUPLICATED, false));
    }
    @PostMapping("/login")
    public ResponseEntity<ResultResponse> login(@RequestBody UserLoginReq userLoginReq) {
        JwtToken token = loginService.login(userLoginReq.getUsername(), userLoginReq.getPassword());
        return ResponseEntity.ok(ResultResponse.of(USER_LOGIN_SUCCESS, token));
    }
}
