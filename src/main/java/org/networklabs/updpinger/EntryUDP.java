package org.networklabs.updpinger;

import java.io.IOException;
import java.util.Random;

public class EntryUDP {
    public static void start() throws IOException {
        var random = new Random();
        var port = random.nextInt(65536 - 1024) + 1024;

        try (var udpServer = new UDPServer(port)) {
            new Thread(() -> {
                try {
                    udpServer.start();
                } catch (IOException e) {
                    System.err.println("A problem with the server occurred: " + e);
                }
            }).start();

            try (var udpClient = new UDPClient()) {
                int i = 0;
                while (i < 10) {
                    i++;
                    udpClient.ping("127.0.0.1", port, 1000);
                }
            }
        }
    }
}
