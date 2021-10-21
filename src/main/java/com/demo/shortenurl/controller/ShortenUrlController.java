package com.demo.shortenurl.controller;

import com.demo.shortenurl.service.ShortenUrlService;
import com.demo.shortenurl.vo.ShortUrl;
import com.demo.shortenurl.vo.ShortUrlGenerateRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
public class ShortenUrlController {

    private final ShortenUrlService service;

    @Autowired
    public ShortenUrlController(ShortenUrlService service) {
        this.service = service;
    }

    @PostMapping(path = "/url/convert", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ShortUrl> convert(@RequestBody ShortUrlGenerateRequest request) {
        return ResponseEntity.ok(service.convert(request.getUrl()));
    }

    @GetMapping(path = "/{shortUrl}")
    public void redirect(@Valid @PathVariable String shortUrl, HttpServletResponse response) throws IOException {
        // validate failed, null or length
        final String requestUrl = StringUtils.trimToNull(shortUrl);
        if (requestUrl == null || requestUrl.length() != 8) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        // Not Found
        String url = service.get(shortUrl);
        if (url == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        response.sendRedirect(url);
    }
}
