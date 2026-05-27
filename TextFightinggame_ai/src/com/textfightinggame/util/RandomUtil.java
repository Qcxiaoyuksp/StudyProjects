package com.textfightinggame.util;

import java.util.Random;

public class RandomUtil {
    private static final Random RANDOM = new Random();
    private static final char[] LETTERS;

    static {
        StringBuilder builder = new StringBuilder();
        for (char c = 'A'; c <= 'Z'; c++) {
            builder.append(c);
        }
        for (char c = 'a'; c <= 'z'; c++) {
            builder.append(c);
        }
        LETTERS = builder.toString().toCharArray();
    }

    public static int randomInt(int min, int max) {
        if (max < min) {
            return min;
        }
        return RANDOM.nextInt(max - min + 1) + min;
    }

    public static String generateUserId() {
        int num = randomInt(10000, 99999);
        return "heima" + num;
    }

    public static String generateCaptcha() {
        char[] chars = new char[5];
        for (int i = 0; i < 4; i++) {
            chars[i] = LETTERS[RANDOM.nextInt(LETTERS.length)];
        }
        char digit = (char) ('0' + RANDOM.nextInt(10));
        int pos = RANDOM.nextInt(5);
        for (int i = 4; i > pos; i--) {
            chars[i] = chars[i - 1];
        }
        chars[pos] = digit;
        return new String(chars);
    }
}

