package com.techeer.checkIt.domain.chat.mapper;

import com.techeer.checkIt.domain.chat.dto.request.CreateMessageReq;
import com.techeer.checkIt.domain.chat.entity.ChatMessage;
import com.techeer.checkIt.domain.chat.entity.ChatRoom;
import com.techeer.checkIt.domain.chat.entity.UserChatRoom;
import com.techeer.checkIt.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static java.time.LocalDateTime.now;

@Component
@RequiredArgsConstructor
public class ChatMapper {

    public UserChatRoom toUserChatRoom(User user,ChatRoom chatRoom) {
        return UserChatRoom.builder()
                .user(user)
                .chatRoom(chatRoom)
                .build();
    }

    public ChatMessage toChatMessage(User user, ChatRoom chatRoom, CreateMessageReq request) {
        return ChatMessage.builder()
                .user(user)
                .chatRoom(chatRoom)
                .content(request.getContent())
                .sendDate(now())
                .build();
    }
}
