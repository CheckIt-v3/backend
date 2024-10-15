package com.techeer.checkIt.domain.book.dto.Response;

import lombok.*;

import java.util.List;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BookResVO {
    private List<Item> items;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
    public static class Item {
        private String description;
    }
}