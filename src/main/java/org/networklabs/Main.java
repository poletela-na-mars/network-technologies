package org.networklabs;

import org.networklabs.proxyserver.EntryProxyServer;
import org.networklabs.proxyserver.ProxyServer;
import org.networklabs.smtp.SMTPClient;
import org.networklabs.updpinger.UDPClient;
import org.networklabs.updpinger.UDPServer;
import org.networklabs.webserver.HttpServer;

import java.io.*;
import java.net.Socket;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        // 1. Web server
//        try (var httpServer = new HttpServer(80)) {
//            httpServer.start();
//        }

        // 2. UPD-pinger
//        var random = new Random();
//        var port = random.nextInt(65536 - 1024) + 1024;
//
//        try (var udpServer = new UDPServer(port)) {
//            new Thread(() -> {
//                try {
//                    udpServer.start();
//                } catch (IOException e) {
//                    System.err.println("A problem with the server occurred: " + e);
//                }
//            }).start();
//
//            try (var udpClient = new UDPClient()) {
//                int i = 0;
//                while (i < 10) {
//                    i++;
//                    udpClient.ping("127.0.0.1", port, 1000);
//                }
//            }
//        }

        // 3. SMTP
//        try (var smtpClient = new SMTPClient()) {
//            smtpClient.openConnection("smtp.gmail.com", 10000, 25);
//            smtpClient.send("network@test.test", "network2@test.test", "Say hello");
//        }

        // 4. ProxyServer
        EntryProxyServer.start();
    }
}
