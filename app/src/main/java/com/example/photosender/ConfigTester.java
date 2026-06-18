package com.example.photosender;

import java.net.InetSocketAddress;
import java.net.Socket;

public class ConfigTester {

    public static int testLatency(String host, int port) {
        long start = System.currentTimeMillis();

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 3000);
        } catch (Exception e) {
            return -1; // fail
        }

        long end = System.currentTimeMillis();
        return (int) (end - start);
    }
}
