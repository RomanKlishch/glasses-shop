package com.rk;

import org.eclipse.jetty.server.Server;

public class Starter {
    public static void main(String[] args) {
        Server server = new Server(8080);



        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
