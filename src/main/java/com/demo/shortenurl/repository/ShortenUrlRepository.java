package com.demo.shortenurl.repository;

import com.demo.shortenurl.domain.ShortenUrl;
import org.springframework.stereotype.Repository;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.util.Map;

@Repository
public class ShortenUrlRepository {

    // key : Base64.encode(MD5.digest(originalUrl))
    private Map<String, ShortenUrl> encodedDatasource;
    // key : Base62.encoded(rand)
    private Map<String, ShortenUrl> indexDatasource;

    public ShortenUrlRepository() {
        encodedDatasource = new ConcurrentReferenceHashMap<>();
        indexDatasource = new ConcurrentReferenceHashMap<>();
    }

    public ShortenUrl findByEncodedUrl(String encodedUrl) {
        return encodedDatasource.get(encodedUrl);
    }

    public ShortenUrl findById(String id) {
        return indexDatasource.get(id);
    }

    public ShortenUrl save(ShortenUrl shortenUrl) {
        encodedDatasource.put(shortenUrl.getHashedUrl(), shortenUrl);
        indexDatasource.put(shortenUrl.getShortenUrl(), shortenUrl);
        return shortenUrl;
    }
}
