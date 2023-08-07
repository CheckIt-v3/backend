package com.techeer.checkIt.domain.user.dto.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLoginReq {
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    @Pattern(
            message = "대소문자나 숫자를 포함한 5-20자리로 입력해야 합니다.",
            regexp = "^[A-Za-z0-9]{5,20}$")
    private String username;
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(
            message = "대소문자와 숫자, 특수문자를 포함한 8자 이상 16자 이하의 비밀번호를 입력해야 합니다.",
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#!~$%^&-+=()])(?=\\S+$).{8,16}$")
    private String password;
}
