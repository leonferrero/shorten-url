package com.demo.shortenurl.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrlGenerateRequest {

    @NotNull
    private String url;
}
