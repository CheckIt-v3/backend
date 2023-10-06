package com.techeer.checkIt.domain.chat.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access =  AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
public class CreateMessageReq {
    @NotBlank(message = "메시지 내용을 입력하세요.")
    private String content;
}
