package com.reharu.rpc.test;

import com.reharu.rpc.proxy.RPCProxyBuilder;
import com.reharu.rpc.test.model.User;
import com.reharu.rpc.test.service.UserService;

public class ClientTest {
    public static void main(String[] args) throws InterruptedException {
        new Thread(ClientTest::run) {
        }.start();
        Thread.sleep(999999);
    }

    public static void run() {
        UserService userService = RPCProxyBuilder.createProxy(UserService.class);
        User user = new User("admin", "admin");

        System.out.println(userService.login(user));

        user = userService.login("111", "admin");
        System.out.println(user);
    }
}
