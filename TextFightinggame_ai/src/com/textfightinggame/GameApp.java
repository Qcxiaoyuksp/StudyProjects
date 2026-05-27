package com.textfightinggame;

import com.textfightinggame.auth.AuthService;
import com.textfightinggame.auth.UserRepository;
import com.textfightinggame.core.GameEngine;
import com.textfightinggame.model.User;

import java.util.Scanner;

public class GameApp {
    private final Scanner scanner = new Scanner(System.in);
    private final UserRepository userRepository = new UserRepository();
    private final AuthService authService = new AuthService();
    private final GameEngine gameEngine = new GameEngine();

    public void run() {
        while (true) {
            printMainMenu();
            String choice = readLine("请选择: ");
            switch (choice) {
                case "1" -> {
                    User user = authService.login(scanner, userRepository);
                    if (user != null) {
                        gameEngine.run(scanner, user);
                        return;
                    }
                }
                case "2" -> authService.register(scanner, userRepository);
                case "3" -> authService.resetPassword(scanner, userRepository);
                case "4" -> {
                    System.out.println("感谢游玩，再见！");
                    return;
                }
                default -> System.out.println("输入有误，请重新选择。\n");
            }
        }
    }

    private void printMainMenu() {
        System.out.println("====================");
        System.out.println("欢迎来到文字格斗游戏 🎮");
        System.out.println("1 登录");
        System.out.println("2 注册");
        System.out.println("3 忘记密码");
        System.out.println("4 退出");
        System.out.println("====================");
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
