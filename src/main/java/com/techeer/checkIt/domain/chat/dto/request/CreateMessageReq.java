package com.techeer.checkIt.domain.chat.dto.request;

import lombok.*;


@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access =  AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
public class CreateMessageReq {
    private String content;
    private String sender;  // username
}
