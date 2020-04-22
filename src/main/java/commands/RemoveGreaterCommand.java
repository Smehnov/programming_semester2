package commands;

import band_data.EnterElementData;
import band_data.MusicBand;
import band_data.MusicBandsData;
import band_data.MusicBandsDataXMLSerializer;
import client.ClientSide;
import server.ServerCommand;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Queue;

public class RemoveGreaterCommand implements Command {
    public RemoveGreaterCommand() {
        CommandExecutor.addCommand("remove_greater", this);
    }

    @Override
    public void execute(String arg, MusicBandsData data) {
        MusicBand musicBand = EnterElementData.createMusicBand();

        try {
            String[] commandParams = new String[1];
            commandParams[0] = MusicBandsDataXMLSerializer.serializeMusicBand(musicBand);
            ServerCommand serverCommand = new ServerCommand("remove_greater", commandParams);
            String message = serverCommand.serializeToString();
            String received = ClientSide.sendMessage(message);
            System.out.println(received);

        } catch (IOException | JAXBException e) {
            e.printStackTrace();
            System.out.println("Can't connect to server, try to enter command again");
        }
    }
}
