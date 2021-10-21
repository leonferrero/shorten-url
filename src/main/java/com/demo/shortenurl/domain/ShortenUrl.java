package com.demo.shortenurl.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShortenUrl {

    private final String originUrl;
    private final String hashedUrl;
    private final String shortenUrl;
    private int requestCount;

    public int increaseAndGetRequestCount() {
        return ++requestCount;
    }
}