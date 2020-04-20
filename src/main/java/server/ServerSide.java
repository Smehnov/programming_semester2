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

    ServerSide() throws IOException {
        this(666);
    }
    String processCommand(ServerCommand serverCommand){
        switch (serverCommand.getType()){
            case "add":


                return "adding band....";

        }


        return "UNKNOWN TYPE OF COMMAND";
    }
    void run() {
        boolean running = true;
        try {

            while (running) {
                buf = new byte[65536];
                DatagramPacket packet
                        = new DatagramPacket(buf, buf.length);


                serverSocket.receive(packet);

                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Received " + received);
                if (received.equals("end")) {
                    running = false;
                    continue;
                }

                String answer = "";
                try {
                    ServerCommand serverCommand = ServerCommand.deserializeFromString(received);
                    answer = processCommand(serverCommand);
                }catch (ClassNotFoundException e){
                    //WRONG COMMAND
                }

                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                byte[] answerBuf = answer.getBytes();
                packet = new DatagramPacket(answerBuf, answerBuf.length, address, port);


                serverSocket.send(packet);
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Can't start server because of io exception");
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSide serverSide = new ServerSide();
        serverSide.run();
    }
}
