package org.networklabs.smtp;

import java.io.*;
import java.net.Socket;

public class SMTPClient implements Closeable {
    public SMTPClient() {
        socket = new Socket();
    }

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private boolean isOpened = false;

    public void openConnection(String server, int timeout, int port) throws IOException {
        socket = new Socket(server, port);
        isOpened = true;
        socket.setSoTimeout(timeout);
        writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        expect(220);
    }

    public void send(String fromAddress, String toAddress, String data) throws IOException {
        if (isOpened) {
            var message = new SMTPMessage(fromAddress, toAddress, data);
            send("HELO people");
            expect(250);

            send("MAIL FROM: " + message.fromAddress);
            expect(250);

            send("RCPT TO: " + message.toAddress);
            expect(250);

            send("DATA");
            expect(354);

            send(message.data);
            expect(250);

            send("QUIT");
            expect(221);
        } else {
            throw new IllegalArgumentException("Connection should be opened");
        }
    }

    private void send(String message) {
        writer.println(message);
    }

    private void expect(int code) throws IOException {
        var line = reader.readLine();
        if (line == null || !line.startsWith(String.valueOf(code)))  {
            throw new IllegalStateException("Expected: " + code + ", but got: " + line);
        }
    }

    @Override
    public void close() throws IOException {
        if (isOpened) {
            socket.close();
        }
    }
}
