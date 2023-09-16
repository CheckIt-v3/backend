package com.techeer.checkIt.domain.chat.dto.request;

import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access =  AccessLevel.PRIVATE)
@Getter
@Builder
public class CreateMessageReq {
    private String content;
    private String sender;

}
