package client;

import sun.nio.ch.DatagramSocketAdaptor;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;

public class ClientSide {
    public static String sendMessage(String msg) throws IOException {
        return sendMessage(msg, "localhost", 666);
    }

    public static String sendMessage(String msg, String address, int port) throws IOException {
        //TODO CHECK SERVER
        InetSocketAddress serverAddress;
        serverAddress = new InetSocketAddress(address, port);
        DatagramChannel client = DatagramChannel.open();
        client.bind(null);

        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        client.send(buffer, serverAddress);
        buffer = ByteBuffer.wrap(new byte[4096]);
        client.receive(buffer);
        String recieved = new String(buffer.array(), StandardCharsets.UTF_8);

        buffer.flip();
        client.close();
        return recieved;
    }

    public static void main(String[] args) {

        try {
            for (int i = 0; i < 100; i++) {
                String recieved = ClientSide.sendMessage("" + i);
                System.out.println("Got: " + recieved);
            }


        } catch (IOException e) {
            System.out.println("Error");
        }

    }
}
