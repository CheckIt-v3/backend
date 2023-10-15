package com.techeer.checkIt.domain.reading.entity;

import com.techeer.checkIt.domain.reading.exception.StatusNotFoundException;

public enum ReadingStatus {
    LIKE,READING,READ;
    public static ReadingStatus convert(String s) {
        switch (s) {
            case "LIKE":
                return ReadingStatus.LIKE;
            case "READING":
                return ReadingStatus.READING;
            case "READ":
                return ReadingStatus.READ;
            default:
                throw new StatusNotFoundException();
        }
    }
}
