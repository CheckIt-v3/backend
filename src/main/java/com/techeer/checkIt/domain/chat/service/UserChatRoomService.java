package com.techeer.checkIt.domain.chat.service;


import com.techeer.checkIt.domain.chat.dto.request.CreateMessageReq;
import com.techeer.checkIt.domain.chat.dto.response.ChatMessageRes;
import com.techeer.checkIt.domain.chat.entity.ChatMessage;
import com.techeer.checkIt.domain.chat.entity.ChatRoom;
import com.techeer.checkIt.domain.chat.entity.UserChatRoom;
import com.techeer.checkIt.domain.chat.exception.ChatRoomNotFoundException;
import com.techeer.checkIt.domain.chat.mapper.ChatMapper;
import com.techeer.checkIt.domain.chat.repository.ChatMessageRepository;
import com.techeer.checkIt.domain.chat.repository.ChatRoomRepository;
import com.techeer.checkIt.domain.chat.repository.UserChatRoomRepository;
import com.techeer.checkIt.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserChatRoomService {
    private final UserChatRoomRepository userChatRoomRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatMapper chatMapper;

    @Transactional
    public ChatRoom createChatRoom() {
        ChatRoom newChatRoom = ChatRoom.builder().build();
        chatRoomRepository.save(newChatRoom);

        return newChatRoom;
    }

    @Transactional
    public UserChatRoom createUserChatRoom(User user, Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(ChatRoomNotFoundException::new);
        UserChatRoom newUserChatRoom = chatMapper.toUserChatRoom(user, chatRoom);

        userChatRoomRepository.save(newUserChatRoom);
        return newUserChatRoom;
    }

    public boolean duplicatedUserChatRoom(User user) {
        return userChatRoomRepository.existsByUserId(user.getId());
    }

    public void saveMessage(User sender, Long chatId, CreateMessageReq createMessageReq) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatId).orElseThrow(ChatRoomNotFoundException::new);
        ChatMessage message = chatMapper.toChatMessage(sender, chatRoom, createMessageReq);

        chatMessageRepository.save(message);
    }

    public List<ChatMessageRes> findChatMessage(User user, Long chatId) {
        List<ChatMessage> chatMessages = chatMessageRepository.findByUserIdAndChatRoomId(user.getId(), chatId);

        return chatMapper.toChatMessageList(chatMessages);
    }
}
