package com.techeer.checkIt.domain.chat.mapper;

import com.techeer.checkIt.domain.chat.entity.ChatRoom;
import com.techeer.checkIt.domain.chat.entity.UserChatRoom;
import com.techeer.checkIt.domain.user.entity.User;
import com.techeer.checkIt.domain.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatRoomMapper {

    public UserChatRoom toUserChatRoom(User user,ChatRoom chatRoom) {
        return UserChatRoom.builder()
                .user(user)
                .chatRoom(chatRoom)
                .build();
    }
}
