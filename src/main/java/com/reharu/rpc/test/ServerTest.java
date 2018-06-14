package com.reharu.rpc.test;

import com.reharu.rpc.server.RPCServer;

import java.io.IOException;

public class ServerTest {
    public static void main(String[] args) throws Exception {
        new Thread(ServerTest::run){}.start();
        Thread.sleep(999999);
    }

    public static void run(){
        RPCServer server = new RPCServer(3839) ;
        try {
            server.startListen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
