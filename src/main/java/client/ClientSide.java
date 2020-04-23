package client;

import special.Constants;
import sun.nio.ch.DatagramSocketAdaptor;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;
import java.util.GregorianCalendar;

public class ClientSide {
    public static String sendMessage(String msg) throws IOException {
        return sendMessage(msg, Constants.getHost(), Constants.getPort());
    }

    public static String sendMessage(String msg, String address, int port) throws IOException {
        //TODO CHECK SERVER

        InetSocketAddress serverAddress;
        serverAddress = new InetSocketAddress(address, port);
        DatagramChannel client = DatagramChannel.open();


        client.bind(null);
        client.socket().setSoTimeout(5000);

        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        client.send(buffer, serverAddress);
        byte[] buf = new byte[65536];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        boolean successfullAttempt = false;

        try {
            client.socket().receive(packet);
            successfullAttempt = true;

        } catch (SocketTimeoutException e) {
            System.out.println("Server is unavailable...");
        }


        String received;
        //String recieved = new String(buffer.array(), StandardCharsets.UTF_8);
        if (successfullAttempt) {
            received = new String(packet.getData(), 0, packet.getLength());
        } else {
            received = "Can't connect to server";
        }
        buffer.flip();
        client.close();
        return received;
    }


}
