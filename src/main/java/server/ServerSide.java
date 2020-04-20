package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class ServerSide {

    DatagramSocket serverSocket;
    byte[] buf;

    ServerSide(int port) throws IOException {
        serverSocket = new DatagramSocket(port);
    }

    ServerSide() throws IOException{
        this(666);
    }

    void run() {
        boolean running = true;
        try {

            while (running) {
                buf = new byte[2048];
                DatagramPacket packet
                        = new DatagramPacket(buf, buf.length);
                serverSocket.receive(packet);
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                String received = new String(packet.getData(), 0, packet.getLength());

                System.out.println("Received " + received);


                if (received.equals("end")) {
                    running = false;
                    continue;
                }


                serverSocket.send(packet);
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Can't start server because of io exception");
        }
    }

    public static void main(String[] args) throws IOException{
        ServerSide serverSide = new ServerSide();
        serverSide.run();
    }
}
