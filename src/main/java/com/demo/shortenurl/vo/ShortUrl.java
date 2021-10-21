package com.demo.shortenurl.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ShortUrl {
    private String url;
    private int count;
}
