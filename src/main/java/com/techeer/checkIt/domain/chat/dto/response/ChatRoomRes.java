package com.techeer.checkIt.domain.chat.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.techeer.checkIt.domain.chat.entity.ChatRoom;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access =  AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ChatRoomRes {
    private ChatRoom chatRoom;
    private String nickname;    // 문의한 회원
}
