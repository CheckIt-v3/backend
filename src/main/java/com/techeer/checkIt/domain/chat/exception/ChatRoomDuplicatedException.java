package com.techeer.checkIt.domain.chat.exception;

import com.techeer.checkIt.global.error.exception.BusinessException;

import static com.techeer.checkIt.global.error.ErrorCode.CHATROOM_DUPLICATED_ERROR;
import static com.techeer.checkIt.global.error.ErrorCode.USER_USERNAME_DUPLICATED;

public class ChatRoomDuplicatedException extends BusinessException {
    public ChatRoomDuplicatedException() {
        super(CHATROOM_DUPLICATED_ERROR);
    }
}
