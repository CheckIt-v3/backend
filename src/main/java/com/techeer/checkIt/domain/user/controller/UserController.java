package com.techeer.checkIt.domain.user.controller;

import com.techeer.checkIt.domain.user.dto.request.UserJoinReq;
import com.techeer.checkIt.domain.user.dto.request.UserLoginReq;
import com.techeer.checkIt.domain.user.dto.request.UserTokenReq;
import com.techeer.checkIt.domain.user.exception.UserDuplicatedException;
import com.techeer.checkIt.domain.user.service.AuthService;
import com.techeer.checkIt.domain.user.service.UserService;
import com.techeer.checkIt.global.jwt.JwtToken;
import com.techeer.checkIt.global.result.ResultCode;
import com.techeer.checkIt.global.result.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    private final AuthService authService;

    @ApiOperation(value = "회원가입")
    @PostMapping("/join")
    public ResponseEntity<ResultResponse> join(
            @RequestBody @Valid UserJoinReq userJoinReq) {
        if (userService.isDuplicatedUsername(userJoinReq.getUsername())) {
            throw new UserDuplicatedException();
        }
        userService.join(userJoinReq);
        return ResponseEntity.ok(ResultResponse.of(USER_REGISTRATION_SUCCESS));
    }

    @ApiOperation(value = "아이디 중복확인")

    @GetMapping("/duplicated/{username}")
    public ResponseEntity<ResultResponse> isDuplicatedUsername(@PathVariable String username) {
        boolean isDuplicated = userService.isDuplicatedUsername(username);

        if (isDuplicated) {
            return ResponseEntity.ok(ResultResponse.of(ResultCode.USER_USERNAME_DUPLICATED, true));
        }
        return ResponseEntity.ok(ResultResponse.of(USER_USERNAME_NOT_DUPLICATED, false));
    }

    @ApiOperation(value = "로그인")

    @PostMapping("/login")
    public ResponseEntity<ResultResponse> login(@RequestBody UserLoginReq userLoginReq) {
        JwtToken token = authService.login(userLoginReq.getUsername(), userLoginReq.getPassword());
        return ResponseEntity.ok(ResultResponse.of(USER_LOGIN_SUCCESS, token));
    }

    @ApiOperation(value = "로그아웃")
    @PostMapping("/logout")
    public ResponseEntity<ResultResponse> logout(@RequestBody @Valid UserTokenReq userTokenReq) {
        authService.logout(userTokenReq);
        return ResponseEntity.ok(ResultResponse.of(USER_LOGOUT_SUCCESS));
    }

    @ApiOperation(value = "토큰 재발급")
    @PostMapping("/reissue")
    public ResponseEntity<ResultResponse> reissue(@RequestBody @Valid UserTokenReq userTokenReq) {
        JwtToken newToken = authService.reissue(userTokenReq);
        return ResponseEntity.ok(ResultResponse.of(USER_REISSUE_SUCCESS, newToken));
    }
}
