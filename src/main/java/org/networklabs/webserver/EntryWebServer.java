package org.networklabs.webserver;

import java.io.IOException;

public class EntryWebServer {
    public static void start() throws IOException {
        try (var httpServer = new HttpServer(80)) {
            httpServer.start();
        }
    }
}
