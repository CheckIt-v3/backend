package com.techeer.checkIt.domain.book.service;

import com.techeer.checkIt.domain.book.dto.Response.BookResVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class BookSearchService {
    private final RestTemplate restTemplate;

    @Value("${naver.client.id}")
    private String CLIENT_ID;

    @Value("${naver.client.secret}")
    private String CLIENT_SECRET;

    private  final String SEARCH_URL = "https://openapi.naver.com/v1/search/book.json";

    public BookSearchService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String getBookDescriptionByTitle(String title){
        final HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", CLIENT_ID);
        headers.set("X-Naver-Client-Secret", CLIENT_SECRET);

        URI targetUrl = UriComponentsBuilder
                .fromUriString(SEARCH_URL)
                .queryParam("query", title)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUri();

        HttpEntity<?> entity = new HttpEntity<>(headers);

        // 네이버 API 요청 보내기
        ResponseEntity<BookResVO> response = restTemplate.exchange(
                targetUrl,
                HttpMethod.GET,
                entity,
                BookResVO.class
        );

        BookResVO bookResponse = response.getBody();
        if (bookResponse != null && !bookResponse.getItems().isEmpty()) {
            return bookResponse.getItems().get(0).getDescription();
        }
        return null;
    }
}
