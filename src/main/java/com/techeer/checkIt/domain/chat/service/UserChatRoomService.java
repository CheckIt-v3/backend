package com.techeer.checkIt.domain.chat.service;


import com.techeer.checkIt.domain.chat.entity.ChatRoom;
import com.techeer.checkIt.domain.chat.entity.UserChatRoom;
import com.techeer.checkIt.domain.chat.exception.ChatRoomNotFoundException;
import com.techeer.checkIt.domain.chat.mapper.ChatRoomMapper;
import com.techeer.checkIt.domain.chat.repository.ChatMessageRepository;
import com.techeer.checkIt.domain.chat.repository.ChatRoomRepository;
import com.techeer.checkIt.domain.chat.repository.UserChatRoomRepository;
import com.techeer.checkIt.domain.user.entity.User;
import com.techeer.checkIt.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserChatRoomService {
    private final UserChatRoomRepository userChatRoomRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMapper chatRoomMapper;

    @Transactional
    public ChatRoom createChatRoom() {
        ChatRoom newChatRoom = ChatRoom.builder().build();
        chatRoomRepository.save(newChatRoom);

        return newChatRoom;
    }

    @Transactional
    public UserChatRoom createUserChatRoom(User user, Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(ChatRoomNotFoundException::new);
        UserChatRoom newUserChatRoom = chatRoomMapper.toUserChatRoom(user, chatRoom);

        userChatRoomRepository.save(newUserChatRoom);

        return newUserChatRoom;
    }

//    public boolean duplicatedUserChatRoom(UserChatRoom userChatRoom) {
//        return userChatRoomRepository.existsByUserChatRoom(userChatRoom.getId());
//    }
}
