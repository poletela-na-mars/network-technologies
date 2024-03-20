package org.networklabs.webserver;

import java.io.*;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class HttpServer implements Closeable {
    public HttpServer(int port) throws IOException {
        socket = new ServerSocket(port);
    }

    final private ServerSocket socket;

    public void start() throws IOException {
        while (true) {
            var client = socket.accept();
            try (BufferedReader inputStream = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
                try (PrintWriter outputStream = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true)) {
                    var line = inputStream.readLine();
                    var requestedPath = getRequestedPath(line);
                    var path = getResourceAbsPath(requestedPath);

                    var file = new File(path.toString());

                    System.out.println("Got a request from client (" + client.getInetAddress() + ":" + client.getPort() + ") for " + requestedPath);
                    if (file.isFile()) {
                        outputStream.println("HTTP/1.1 200 OK");
                        outputStream.println("Content-Type: text/plain;charset=UTF-8\n");
                        var fileContent = Files.readAllBytes(path);
                        var strFileContent = new String(fileContent, StandardCharsets.UTF_8);
                        outputStream.println(strFileContent);
                        System.out.println("Sent a response to client (" + client.getInetAddress() + ":" + client.getPort() + ") for " + requestedPath);
                    } else {
                        System.out.println("File on path " + requestedPath + " is not found");
                        outputStream.println("HTTP/1.1 404 Not Found");
                    }
                }
            }
        }
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }

    private String getRequestedPath(String requestHeader) {
        return requestHeader.split(" ")[1];
    }

    private Path getResourceAbsPath(String reqPath) {
        var path = Path.of(reqPath);
        if (!path.isAbsolute()) {
            path = Path.of(new File("").getAbsoluteFile() + (path.toString()));
        }

        return path;
    }
}
