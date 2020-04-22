package commands;

import band_data.EnterElementData;
import band_data.MusicBand;
import band_data.MusicBandsData;
import band_data.MusicBandsDataXMLSerializer;
import client.ClientSide;
import server.ServerCommand;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public class AddIfMinCommand implements Command {
    public AddIfMinCommand() {
        CommandExecutor.addCommand("add_if_min", this);
    }

    @Override
    public void execute(String arg, MusicBandsData data) {
        MusicBand musicBand = EnterElementData.createMusicBand();
        try {
            String[] commandParams = new String[1];
            commandParams[0] = MusicBandsDataXMLSerializer.serializeMusicBand(musicBand);
            ServerCommand serverCommand = new ServerCommand("add_if_min", commandParams);
            String message = serverCommand.serializeToString();

            String received = ClientSide.sendMessage(message);
            System.out.println(received);

        } catch (IOException | JAXBException e) {
            e.printStackTrace();
            System.out.println("Can't connect to server, try to enter command again");
        }

    }
}
