package com.techeer.checkIt.domain.reading.entity;

import com.techeer.checkIt.domain.reading.exception.StatusNotFoundException;

public enum ReadingStatus {
    UNREAD,READING,READ, StatusNotFoundException;
    public static ReadingStatus convert(String s) {
        switch (s) {
            case "UNREAD":
                return ReadingStatus.UNREAD;
            case "READING":
                return ReadingStatus.READING;
            case "READ":
                return ReadingStatus.READ;
            default:
                throw new StatusNotFoundException();
        }
    }
}
