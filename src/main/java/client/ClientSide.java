package client;

import sun.nio.ch.DatagramSocketAdaptor;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;

public class ClientSide {
    DatagramChannel client;
    InetSocketAddress serverAddress;
    ClientSide(int serverPort, String serverIp) throws IOException {
        serverAddress = new InetSocketAddress(serverIp, serverPort);
        client = DatagramChannel.open();
        client.bind(null);
    }
    ClientSide() throws IOException{
        this(666, "localhost");
    }

    String sendMessage(String msg) throws IOException{
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        client.send(buffer, serverAddress);
        buffer.clear();
        client.receive(buffer);
        String recieved = new String(buffer.array(), StandardCharsets.UTF_8);

        buffer.flip();
        return recieved;
    }

    public static void main(String[] args) {

        try {
            for (int i = 0; i < 100; i++) {
                ClientSide client = new ClientSide();
                String recieved = client.sendMessage("Number "+i);
                System.out.println("Got: "+recieved);
            }


        } catch (IOException e) {
            System.out.println("Error");
        }

    }
}
