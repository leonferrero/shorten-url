package com.demo.shortenurl.service;

import com.demo.shortenurl.vo.ShortUrl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShortenUrlServiceTest {

    @Autowired
    ShortenUrlService service;

    @Test
    public void convertAndGetTest() {

        String originUrl = "https://magazine.musinsa.com/?m=magazine&uid=16798";
        ShortUrl originConvert = service.convert(originUrl);
        Assertions.assertNotNull(originConvert);
        System.out.println(originConvert);


        String encodedUrl = "https%3A%2F%2Fmagazine.musinsa.com%2F%3Fm%3Dmagazine%26uid%3D16798";
        ShortUrl encodedConvert = service.convert(encodedUrl);
        Assertions.assertNotNull(encodedConvert);
        System.out.println(encodedConvert);


        Assertions.assertEquals(originConvert.getUrl(), encodedConvert.getUrl());
        Assertions.assertNotEquals(originConvert.getCount(), encodedConvert.getCount());
        Assertions.assertTrue(encodedConvert.getCount() > originConvert.getCount());

        String str = originConvert.getUrl().substring("http://localhost/".length());
        System.out.println(str);
        String url = service.get(str);
        System.out.println(url);
        Assertions.assertNotNull(url);
        Assertions.assertEquals(url, originUrl);
    }
}