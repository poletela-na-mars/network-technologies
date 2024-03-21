package org.networklabs.updpinger;

import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

public class UDPClient implements Closeable {
    public UDPClient() throws IOException {
        socket = new DatagramSocket();
    }

    final private DatagramSocket socket;
    private int pingIdx = -1;
    private final byte[] receivingDataBuffer = new byte[1048];

    public void ping(String server, int port, int timeout) throws IOException {
        pingIdx++;
        socket.setSoTimeout(timeout);

        var startTime = LocalDateTime.now();
        var message = "C: Pinging " + server + ", #" + pingIdx + " at " + startTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        var messageArr = message.getBytes();
        var serverAddress = InetAddress.getByName(server);
        var packet = new DatagramPacket(messageArr, messageArr.length, serverAddress, port);

        socket.send(packet);
        System.out.println("C: Sent ping to a server " + serverAddress.getHostAddress() + ":" + port);

        var receivedPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
        try {
            socket.receive(receivedPacket);
        } catch (SocketTimeoutException e) {
            System.out.println("C: #" + pingIdx + " Request timeout");
            return;
        }
        var endTime = LocalDateTime.now();
        var receivedMsg = new String(receivedPacket.getData(), 0, receivedPacket.getLength());
        var rtt = endTime.getLong(ChronoField.MILLI_OF_SECOND) - startTime.getLong(ChronoField.MILLI_OF_SECOND);
        System.out.println("C: #" + pingIdx + " | " + receivedMsg + " | RTT: " + rtt);
    }

    @Override
    public void close() {
        socket.close();
    }
}
