package org.networklabs;

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
        var isUsingBrowser = false;
        try (var proxyServer = new ProxyServer("google.com", 80, 44235);
             var socket = new Socket("127.0.0.1", 44235);
             var writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
             var reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            new Thread(() -> {
                try {
                    proxyServer.start(!isUsingBrowser);
                } catch (IOException e) {
                    System.out.println("Server error");
                    throw new RuntimeException(e);
                }
            }).start();
            if (!isUsingBrowser) {
                new Thread(() -> {
                    while (!proxyServer.isReady()) ;
                    System.out.println("Sending GET request");
                    writer.println("GET /index.html");

                    System.out.println("Response from server: ");
                    try {
                        while (proxyServer.isReady()) {
                            var respLine = reader.readLine();
                            System.out.println(respLine);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
            while (proxyServer.isReady()) ;
        }
    }
}
