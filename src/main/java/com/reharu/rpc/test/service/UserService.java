package com.reharu.rpc.test.service;

import com.reharu.rpc.test.model.User;

public interface UserService {

    boolean login(User user) ;

    User login(String userName, String password) ;
}
