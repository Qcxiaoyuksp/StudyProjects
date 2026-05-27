package com.textfightinggame;

import com.textfightinggame.auth.ValidationUtil;
import com.textfightinggame.util.RandomUtil;

public class TestHarness {
    public static void main(String[] args) {
        System.out.println("Validation username abc123: " + ValidationUtil.isUsernameValid("abc123"));
        System.out.println("Validation username 123: " + ValidationUtil.isUsernameValid("123"));
        System.out.println("Validation password ab1: " + ValidationUtil.isPasswordValid("ab1"));
        System.out.println("Captcha example: " + RandomUtil.generateCaptcha());
        System.out.println("Smoke test done.");
    }
}

