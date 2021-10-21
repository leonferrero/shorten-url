package com.demo.shortenurl.utils;

public class Base62 {

    private static final int BASE62 = 62;
    private static final String BASE62_CHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String encode(long num) {
        if (num < 0)
            throw new IllegalArgumentException("invalid argument. must be positive number.");
        if (num == 0)
            return BASE62_CHAR.substring(0, 1);
        StringBuffer sb = new StringBuffer();
        while (num > 0) {
            sb.append(BASE62_CHAR.charAt((int) (num % BASE62)));
            num /= BASE62;
        }
        return sb.reverse().toString();
    }

    public static long decode(String str) {
        long sum = 0;
        for (int i = str.length() - 1; i >= 0; i--)
            sum += BASE62_CHAR.indexOf(str.charAt(i)) * Math.pow(BASE62, str.length() - i - 1);
        return sum;
    }
}
