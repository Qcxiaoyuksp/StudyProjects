package com.textfightinggame.auth;

import com.textfightinggame.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final List<User> users = new ArrayList<>();

    public void add(User user) {
        users.add(user);
    }

    public boolean usernameExists(String username) {
        return findByUsername(username) != null;
    }

    public User findByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}

