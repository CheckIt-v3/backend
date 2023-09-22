package com.techeer.checkIt.domain.chat.exception;

import com.techeer.checkIt.global.error.ErrorCode;
import com.techeer.checkIt.global.error.exception.BusinessException;

public class ChatRoomNotFoundException extends BusinessException {
    public ChatRoomNotFoundException() {
        super(ErrorCode.CHATROOM_NOT_FOUND_ERROR);
    }
}
