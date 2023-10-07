package com.techeer.checkIt.domain.chat.mapper;

import com.techeer.checkIt.domain.chat.dto.request.CreateMessageReq;
import com.techeer.checkIt.domain.chat.dto.response.ChatMessageRes;
import com.techeer.checkIt.domain.chat.entity.ChatMessage;
import com.techeer.checkIt.domain.chat.entity.ChatRoom;
import com.techeer.checkIt.domain.chat.entity.UserChatRoom;
import com.techeer.checkIt.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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

    public ChatMessageRes toChatMessageDto(ChatMessage chatMessage) {
        return ChatMessageRes.builder()
                .sender(chatMessage.getUser().getNickname())
                .content(chatMessage.getContent())
                .sendDate(chatMessage.getSendDate())
                .build();
    }

    public List<ChatMessageRes> toChatMessageList(List<ChatMessage> messages) {
        return messages.stream().map(this::toChatMessageDto).collect(Collectors.toList());
    }
}
