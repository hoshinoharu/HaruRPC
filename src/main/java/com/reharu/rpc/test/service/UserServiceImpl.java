package com.reharu.rpc.test.service;

import com.reharu.rpc.test.model.User;

public class UserServiceImpl implements UserService {

    @Override
    public boolean login(User user) {
        boolean success = false;
        if (daoSelectByUserNameAndPassword(user.name, user.password) != null) {
            success = true;
        }
        return success;
    }

    @Override
    public User login(String userName, String password) {
        User user = daoSelectByUserNameAndPassword(userName, password);
        return user;
    }

    private User daoSelectByUserNameAndPassword(String userName, String password) {
        if (userName.equals("admin") && password.equals("admin")) {
            return new User(userName, password);
        }
        return null;
    }
}
