package com.demo.shortenurl.utils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

public class UrlDecodeUtil {

    private final static Pattern pattern = Pattern.compile("%[0-9a-f]{2}", Pattern.CASE_INSENSITIVE);

    private static boolean isEncoded(String url) {
        return pattern.matcher(url).find();
    }

    public static String decode(final String url) {
        StringBuffer sb = new StringBuffer(url);
        while (isEncoded(sb.toString()))
            sb = new StringBuffer(URLDecoder.decode(sb.toString(), StandardCharsets.UTF_8));
        return sb.toString();
    }
}
