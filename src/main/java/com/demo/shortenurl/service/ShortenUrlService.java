package com.demo.shortenurl.service;

import com.demo.shortenurl.domain.ShortenUrl;
import com.demo.shortenurl.exception.InvalidRequestException;
import com.demo.shortenurl.repository.ShortenUrlRepository;
import com.demo.shortenurl.utils.ShortUrlGenerator;
import com.demo.shortenurl.utils.UrlDecodeUtil;
import com.demo.shortenurl.vo.ShortUrl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class ShortenUrlService {

    private final static String PREFIX = "http://localhost/";
    private final MessageDigest md5;
    private final ShortenUrlRepository repository;

    @Autowired
    public ShortenUrlService(ShortenUrlRepository repository) throws NoSuchAlgorithmException {
        this.repository = repository;
        md5 = MessageDigest.getInstance("MD5");
    }

    public ShortUrl convert(String originUrl) {

        String tmpUrl = StringUtils.trimToNull(originUrl);
        if (tmpUrl == null)
            throw new InvalidRequestException();

        try {
            new URL(UrlDecodeUtil.decode(tmpUrl));
        } catch (MalformedURLException e) {
            throw new InvalidRequestException();
        }

        String hasedUrl = Base64Utils.encodeToString(md5.digest(tmpUrl.getBytes(StandardCharsets.UTF_8)));
        ShortenUrl shortenUrl = repository.findByEncodedUrl(hasedUrl);
        if (shortenUrl == null) {
            shortenUrl = repository.save(
                    ShortenUrl.builder()
                            .hashedUrl(hasedUrl)
                            .shortenUrl(ShortUrlGenerator.genernate())
                            .originUrl(originUrl)
                            .requestCount(0)
                            .build()
            );
        }
        return ShortUrl.builder()
                .url(PREFIX + shortenUrl.getShortenUrl())
                .count(shortenUrl.increaseAndGetRequestCount())
                .build();
    }

    public String get(String shortUrl) {
        ShortenUrl shortenUrl = repository.findById(shortUrl);
        return shortenUrl != null ? shortenUrl.getOriginUrl() : null;
    }
}
