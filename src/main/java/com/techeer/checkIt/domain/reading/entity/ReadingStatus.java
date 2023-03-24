package com.techeer.checkIt.domain.reading.entity;

public enum ReadingStatus {
    UNREAD,READING,READ;
    public static ReadingStatus convert(String s) {
        switch (s) {
            case "UNREAD":
                return ReadingStatus.UNREAD;
            case "READING":
                return ReadingStatus.READING;
            case "READ":
                return ReadingStatus.READ;
        }
        return null;
    }
}
