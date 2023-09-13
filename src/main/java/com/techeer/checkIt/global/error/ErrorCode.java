package com.techeer.checkIt.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** {주체}_{이유} message 는 동사 명사형으로 마무리 */
@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Global
    INTERNAL_SERVER_ERROR(500, "G001", "서버 오류"),
    INPUT_INVALID_VALUE(409, "G002", "잘못된 입력"),
    ACCESS_INVALID_VALUE(400, "G003", "잘못된 접근"),

    // User 도메인
    INVALID_PASSWORD(400, "U001", "잘못된 비밀번호"),
    USER_NOT_FOUND_ERROR(400, "U002", "사용자를 찾을 수 없음"),
    UNAUTHORIZED_ACCESS_ERROR(403, "U003", "승인되지 않은 접근"),
    USER_USERNAME_DUPLICATED(409, "U004", "회원 아이디 중복"),


    // Book 도메인
    BOOK_NOT_FOUND_ERROR(400, "B001","책을 찾을 수 없음"),

    // Reading 도메인
    READING_NOT_FOUND_ERROR(400,"R001","독서정보를 찾을 수 없음"),
    READING_STATUS_NOT_FOUND_ERROR(400,"R002","상태가 올바르지 않음"),
    PAGE_VALIDATION_NEGATIVE_VALUE(400, "R003", "입력한 값이 작아 음수가 반횐됨"),
    PAGE_VALIDATION_OUT_OF_PAGE(400 ,"R004", "책의 페이지 범위를 벗어남"),
    READING_DUPLICATED_ERROR(400 ,"R005", "이미 등록된 책"),

    // ReadingVolume 도메인
    READING_VOLUME_NOT_FOUND_ERROR(400, "RV001", "독서추세를 찾을 수 없음"),

    ;

    private final int status;
    private final String code;
    private final String message;
}
