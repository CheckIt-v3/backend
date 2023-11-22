package com.techeer.checkIt.domain.chat.service;


import com.techeer.checkIt.domain.chat.dto.request.CreateMessageReq;
import com.techeer.checkIt.domain.chat.dto.response.ChatMessageRes;
import com.techeer.checkIt.domain.chat.dto.response.ChatRoomRes;
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
    public void createUserChatRoom(User user, Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(ChatRoomNotFoundException::new);
        UserChatRoom newUserChatRoom = chatMapper.toUserChatRoom(user, chatRoom);

        userChatRoomRepository.save(newUserChatRoom);
    }

    public boolean duplicatedUserChatRoom(User user) {
        return userChatRoomRepository.existsByUserId(user.getId());
    }

    public UserChatRoom findUserChatRoomByUserId(Long userId) {
        return userChatRoomRepository.findByUserId(userId).orElseThrow(ChatRoomNotFoundException::new); // TODO: 에러처리 수정
    }

    public void saveMessage(User sender, Long chatRoomId, CreateMessageReq createMessageReq) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(ChatRoomNotFoundException::new);
        ChatMessage message = chatMapper.toChatMessage(sender, chatRoom, createMessageReq);

        chatMessageRepository.save(message);
    }

    public List<ChatMessageRes> findChatMessage(Long chatRoomId) {
        List<ChatMessage> chatMessages = chatMessageRepository.findByChatRoomId(chatRoomId);

        return chatMapper.toChatMessageList(chatMessages);
    }

    public List<ChatRoomRes> findChatRoom() {
        List<UserChatRoom> chatRooms = userChatRoomRepository.findAll();

        return chatMapper.toChatRoomList(chatRooms);
    }
}
