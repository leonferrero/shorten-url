package com.demo.shortenurl.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UrlDecodeUtilTest {

    @Test
    void recursiveEncodedUrl() {

        String originUrl = "https://magazine.musinsa.com/index.php?m=magazine&uid=16800";
        String[] encodedUrls = {
                "https://magazine.musinsa.com/index.php?m=magazine&uid=16800",
                "https%3A%2F%2Fmagazine.musinsa.com%2Findex.php%3Fm%3Dmagazine%26uid%3D16800",
                "https%253A%252F%252Fmagazine.musinsa.com%252Findex.php%253Fm%253Dmagazine%2526uid%253D16800"
        };

        for (String encoded : encodedUrls) {
            System.out.println(String.format("encoded : %s", encoded));
            String decoded = UrlDecodeUtil.decode(encoded);
            System.out.println(String.format("decoded : %s", decoded));
            Assertions.assertEquals(originUrl, decoded);;
        }
    }
}