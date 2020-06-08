package client;

import band_data.MusicBand;
import band_data.MusicBandsData;
import band_data.MusicBandsDataXMLSerializer;
import server.ServerCommand;
import special.Constants;
import sun.nio.ch.DatagramSocketAdaptor;

import javax.xml.bind.JAXBException;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class ClientSide {
    public static String sendMessage(String msg) throws IOException {
        return (String)sendMessage(msg, Constants.getHost(), Constants.getPort(), false)[0];
    }

    public static Object[] sendGUIMessage(String msg)throws  IOException{
        return sendMessage(msg, Constants.getHost(), Constants.getPort(), true);
    }

    public static Object[] sendMessage(
            String msg, String address, int port, boolean fromGUI) throws IOException {
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
        String received;
        try {
            client.socket().receive(packet);
            successfullAttempt = true;

        } catch (SocketTimeoutException e) {
            System.out.println("Server is unavailable...");
        }



        //String recieved = new String(buffer.array(), StandardCharsets.UTF_8);
        if (successfullAttempt) {
            received = new String(packet.getData(), 0, packet.getLength());
        } else {
            received = "Can't connect to server";
        }
        buffer.flip();
        client.close();
        if(!fromGUI) {
            try {
                ServerCommand serverAnswer = ServerCommand.deserializeFromString(received);
                if (serverAnswer.getType().equals("message")) {
                    return new String[]{serverAnswer.getParams()[0]};
                }
                if(serverAnswer.getType().equals("bands")){
                    try {
                        ArrayList<MusicBand> bands = new ArrayList<>();
                        String msgg = "Queue elements: \n";
                        for (String bandString:
                             serverAnswer.getParams()) {
                            MusicBand band = MusicBandsDataXMLSerializer.readMusicBandFromXMLString(bandString);
                            bands.add(band);
                            msgg+=band+"\n";
                        }
                        return new String[]{msgg};



                    }catch (JAXBException e){
                        return new String[]{"Can't connect to server"};
                    }
                    }

            } catch (ClassNotFoundException | NullPointerException e) {
                return new String[]{"Can't connect to server"};
            }
            return new String[]{"Can't connect to server"};
        }else{
            try {
                ServerCommand serverAnswer = ServerCommand.deserializeFromString(received);
                if (serverAnswer.getType().equals("message")) {
                    return new String[]{"message",serverAnswer.getParams()[0]};
                }
                if (serverAnswer.getType().equals("bands")) {
                    try {
                        ArrayList<MusicBand> bands = new ArrayList<>();
                        for (String bandString:
                                serverAnswer.getParams()) {
                            MusicBand band = MusicBandsDataXMLSerializer.readMusicBandFromXMLString(bandString);
                            bands.add(band);
                        }
                        return new Object[]{"bands", bands};
                    }catch (JAXBException e){
                        return new String[]{"message", "Can't connect to server"};
                    }

                    }
            } catch (ClassNotFoundException | NullPointerException e) {
                return new String[]{"message","Can't connect to server"};
            }
            return new String[]{"message","Can't connect to server"};
        }
    }


}
