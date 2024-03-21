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

    private byte[] receivingDataBuffer = new byte[1048];
    private byte[] sendingDataBuffer = new byte[1048];

    public void start() throws IOException {
        System.out.println("S: Server has started at port " + socket.getLocalPort());
        var random = new Random();

        while (true) {
            var inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);

            System.out.println("---------------------------------");
            System.out.println("S: Waiting for a client's message");

            if (random.nextInt(10) < 4) {
                System.out.println("S: Packet drop!");
                continue;
            }

            try {
                socket.receive(inputPacket);
            } catch (IOException e) {
                break;
            }

            var receivedData = new String(inputPacket.getData(), 0, inputPacket.getLength());

            System.out.println("S: Received a client's (" + inputPacket.getAddress().getHostAddress() +
                    ":" + inputPacket.getPort() + ")" + " message: " + receivedData);

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
