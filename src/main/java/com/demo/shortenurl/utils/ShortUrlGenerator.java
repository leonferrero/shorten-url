package com.demo.shortenurl.utils;

import java.util.concurrent.ThreadLocalRandom;

public class ShortUrlGenerator {

    public static String genernate() {
        return Base62.encode(ThreadLocalRandom.current().nextLong(214818490978687L) + 3521614606208L);
    }
}
