package org.networklabs.updpinger;

import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Random;

public class UDPServer implements Closeable {
    public UDPServer(int port) throws IOException {
        socket = new DatagramSocket(port);
    }

    final private DatagramSocket socket;

    private byte[] receivingDataBuffer = new byte[1024];
    private byte[] sendingDataBuffer = new byte[1024];

    public void start() throws IOException {
        while (true) {
            System.out.println("S: Server has started at port " + socket.getLocalPort());
            var inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);

            System.out.println("S: Waiting for a client's message");
            socket.receive(inputPacket);

            var receivedData = new String(inputPacket.getData());

            System.out.println("S: Received a client's (" + inputPacket.getAddress().getHostAddress() +
                    ":" + inputPacket.getPort() + ")" + " message: " + receivedData);

            var random = new Random();

            if (random.nextInt(10) < 4) {
                System.out.println("S: Packet drop!");
                continue;
            }

            sendingDataBuffer = receivedData.toUpperCase().getBytes();

            var senderAddress = inputPacket.getAddress();
            var senderPort = inputPacket.getPort();

            var outputPacket = new DatagramPacket(sendingDataBuffer, sendingDataBuffer.length, senderAddress, senderPort);
            socket.send(outputPacket);

            System.out.println("S: Sent a response to a client: " + outputPacket.getAddress().getHostAddress() + ":" + outputPacket.getPort());
        }
    }

    @Override
    public void close() {
        socket.close();
    }
}
