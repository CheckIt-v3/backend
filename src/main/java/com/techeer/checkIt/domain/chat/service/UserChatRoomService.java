package com.techeer.checkIt.domain.chat.service;


import com.techeer.checkIt.domain.chat.dto.request.CreateMessageReq;
import com.techeer.checkIt.domain.chat.entity.ChatMessage;
import com.techeer.checkIt.domain.chat.entity.ChatRoom;
import com.techeer.checkIt.domain.chat.entity.UserChatRoom;
import com.techeer.checkIt.domain.chat.exception.ChatRoomNotFoundException;
import com.techeer.checkIt.domain.chat.mapper.ChatRoomMapper;
import com.techeer.checkIt.domain.chat.repository.ChatMessageRepository;
import com.techeer.checkIt.domain.chat.repository.ChatRoomRepository;
import com.techeer.checkIt.domain.chat.repository.UserChatRoomRepository;
import com.techeer.checkIt.domain.user.entity.User;
import com.techeer.checkIt.domain.user.exception.UserNotFoundException;
import com.techeer.checkIt.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserChatRoomService {
    private final UserChatRoomRepository userChatRoomRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
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


    //    @Transactional
    public void save(Long chatId, CreateMessageReq createMessageReq) {
        User sender = userRepository.findById(createMessageReq.getUserId()).orElseThrow(UserNotFoundException::new);
        ChatRoom chatRoom = chatRoomRepository.findById(chatId).orElseThrow(ChatRoomNotFoundException::new);
        ChatMessage message = ChatMessage.builder()
                .user(sender)
                .chatRoom(chatRoom)
                .content(createMessageReq.getContent())
                .build();
        chatMessageRepository.save(message);
    }
}
