package com.example.photosender;

import java.net.InetSocketAddress;
import java.net.Socket;

public class ConfigSelector {

    public static int test(String host, int port) {

        long start = System.currentTimeMillis();

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 2500);
        } catch (Exception e) {
            return -1;
        }

        return (int) (System.currentTimeMillis() - start);
    }
}
