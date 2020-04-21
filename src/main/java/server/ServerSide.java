package server;

import band_data.MusicBand;
import band_data.MusicBandsData;
import band_data.MusicBandsDataXMLSerializer;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ServerSide {
    final String nameOfEnvVar = "LAB_DATA_PATH";
    MusicBandsData musicBandsData;
    DatagramSocket serverSocket;
    byte[] buf;
    BufferedReader bufferedReader;

    ServerSide(int port) throws IOException {
        serverSocket = new DatagramSocket(port);
        serverSocket.setSoTimeout(1000);

        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        readMusicBand();

    }

    void readMusicBand(){
        musicBandsData = new MusicBandsData();
        String dataPath = System.getenv(nameOfEnvVar);

        if (dataPath == null) {
            System.out.println("ERROR\nYou need to set environment variable(LAB_DATA_PATH) with path to you data file(.xml)");
            System.out.println("Exit...");
            System.exit(0);
        } else {
            System.out.println("Getting data from file " + dataPath + " ...");
            try {
                musicBandsData = MusicBandsDataXMLSerializer.readFromXML(dataPath);
                System.out.println("Got data");

            } catch (JAXBException e) {
                e.printStackTrace();
                System.out.println("error while reading data from XML");
                System.out.println("Exit...");
                System.exit(0);
            }

        }
    }

    ServerSide() throws IOException {
        this(666);
    }

    String processCommand(ServerCommand serverCommand) {
        switch (serverCommand.getType()) {
            case "add":
                try {
                    MusicBand musicBand = MusicBandsDataXMLSerializer.readMusicBandFromXMLString(serverCommand.getParams()[0]);
                    musicBandsData.addMusicBand(musicBand);
                    String msg = "New element was added:\n" + musicBand.toString();

                    return msg;
                }catch (JAXBException e){
                    return "Error while extracting music band data on server...";
                }
        }


        return "UNKNOWN TYPE OF COMMAND";
    }

    void saveToFile() {
        System.out.println("Saving to file ...");
    }

    void processInput(String s) {
        switch (s) {
            case "exit":
                saveToFile();
                System.out.println("EXIT...");
                System.exit(0);
                break;
        }
    }

    void run() {
        boolean running = true;
        try {

            while (running) {
                buf = new byte[65536];
                DatagramPacket packet
                        = new DatagramPacket(buf, buf.length);

                try {
                    serverSocket.receive(packet);
                } catch (SocketTimeoutException e) {
                    if (bufferedReader.ready()) {
                        processInput(bufferedReader.readLine());
                    }

                    continue;
                }

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
                } catch (ClassNotFoundException e) {
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

    public static void runServerSide() throws IOException {
        ServerSide serverSide = new ServerSide();
        serverSide.run();
    }
}
