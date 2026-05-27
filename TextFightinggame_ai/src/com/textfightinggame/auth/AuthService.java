package com.textfightinggame.auth;

import com.textfightinggame.model.User;
import com.textfightinggame.util.RandomUtil;

import java.util.Scanner;

public class AuthService {
    public void register(Scanner scanner, UserRepository repository) {
        System.out.println("\n--- 注册 ---");
        String username;
        while (true) {
            System.out.print("请输入用户名: ");
            username = scanner.nextLine().trim();
            if (!ValidationUtil.isUsernameValid(username)) {
                System.out.println("用户名不符合规则，请重试。");
                System.out.println("用户名规则: 3~16 位字母/数字，不能为纯数字。");
                continue;
            }
            if (repository.usernameExists(username)) {
                System.out.println("用户名已存在，请重试。");
                continue;
            }
            break;
        }

        String password;
        while (true) {
            System.out.print("请输入密码: ");
            password = scanner.nextLine().trim();
            if (!ValidationUtil.isPasswordValid(password)) {
                System.out.println("密码不符合规则，请重试。");
                System.out.println("密码规则: 3~8 位字母/数字，且必须同时包含字母和数字。");
                continue;
            }
            System.out.print("请再次输入密码: ");
            String confirm = scanner.nextLine().trim();
            if (!password.equals(confirm)) {
                System.out.println("两次密码不一致，请重试。");
                continue;
            }
            break;
        }

        String phone;
        while (true) {
            System.out.print("请输入手机号: ");
            phone = scanner.nextLine().trim();
            if (!ValidationUtil.isPhoneValid(phone)) {
                System.out.println("手机号不符合规则，请重试。");
                System.out.println("手机号规则: 11 位数字，且首位为 1。");
                continue;
            }
            break;
        }

        User user = new User(RandomUtil.generateUserId(), username, password, phone, true);
        repository.add(user);
        System.out.println("注册成功，欢迎你，" + username + "！🎉\n");
    }

    public User login(Scanner scanner, UserRepository repository) {
        System.out.println("\n--- 登录 ---");
        System.out.print("请输入用户名: ");
        String username = scanner.nextLine().trim();
        User user = repository.findByUsername(username);
        if (user == null) {
            System.out.println("用户名不存在，请先注册。\n");
            return null;
        }
        if (!user.isStatus()) {
            System.out.println("账号已被禁用，请联系客服。\n");
            return null;
        }

        for (int attempt = 1; attempt <= 3; attempt++) {
            System.out.print("请输入密码: ");
            String password = scanner.nextLine().trim();
            if (!user.getPassword().equals(password)) {
                System.out.println("密码错误。\n");
                if (attempt == 3) {
                    lockUser(user);
                }
                continue;
            }

            String captcha = RandomUtil.generateCaptcha();
            System.out.println("验证码: " + captcha);
            System.out.print("请输入验证码: ");
            String inputCaptcha = scanner.nextLine().trim();
            if (!captcha.equalsIgnoreCase(inputCaptcha)) {
                System.out.println("验证码错误。\n");
                if (attempt == 3) {
                    lockUser(user);
                }
                continue;
            }

            System.out.println("登录成功，欢迎回来，" + user.getUsername() + "！✅\n");
            return user;
        }
        return null;
    }

    public void resetPassword(Scanner scanner, UserRepository repository) {
        System.out.println("\n--- 忘记密码 ---");
        System.out.print("请输入用户名: ");
        String username = scanner.nextLine().trim();
        User user = repository.findByUsername(username);
        if (user == null) {
            System.out.println("用户名不存在。\n");
            return;
        }

        System.out.print("请输入绑定手机号: ");
        String phone = scanner.nextLine().trim();
        if (!user.getPhone().equals(phone)) {
            System.out.println("手机号验证失败。\n");
            return;
        }

        String password;
        while (true) {
            System.out.print("请输入新密码: ");
            password = scanner.nextLine().trim();
            if (!ValidationUtil.isPasswordValid(password)) {
                System.out.println("密码不符合规则，请重试。");
                System.out.println("密码规则: 3~8 位字母/数字，且必须同时包含字母和数字。");
                continue;
            }
            System.out.print("请再次输入新密码: ");
            String confirm = scanner.nextLine().trim();
            if (!password.equals(confirm)) {
                System.out.println("两次密码不一致，请重试。");
                continue;
            }
            break;
        }

        user.setPassword(password);
        System.out.println("密码重置成功，请使用新密码登录。✅\n");
    }

    private void lockUser(User user) {
        user.setStatus(false);
        System.out.println("错误次数过多，账号已被禁用，请联系客服。\n");
    }
}
