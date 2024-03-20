package org.networklabs;

import org.networklabs.webserver.HttpServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1. Web server
        try (HttpServer httpServer = new HttpServer(80)) {
            httpServer.start();
        }
    }
}