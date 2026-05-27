package com.textfightinggame.auth;

public class ValidationUtil {
    public static boolean isUsernameValid(String username) {
        if (username == null || username.length() < 3 || username.length() > 16) {
            return false;
        }
        boolean hasLetter = false;
        for (char c : username.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                return false;
            }
            if (Character.isLetter(c)) {
                hasLetter = true;
            }
        }
        return hasLetter;
    }

    public static boolean isPasswordValid(String password) {
        if (password == null || password.length() < 3 || password.length() > 8) {
            return false;
        }
        boolean hasLetter = false;
        boolean hasDigit = false;
        for (char c : password.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                return false;
            }
            if (Character.isLetter(c)) {
                hasLetter = true;
            }
            if (Character.isDigit(c)) {
                hasDigit = true;
            }
        }
        return hasLetter && hasDigit;
    }

    public static boolean isPhoneValid(String phone) {
        if (phone == null || phone.length() != 11) {
            return false;
        }
        if (phone.charAt(0) != '1') {
            return false;
        }
        for (char c : phone.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
