package commands;

import band_data.EnterElementData;
import band_data.MusicBand;
import band_data.MusicBandsData;
import band_data.MusicBandsDataXMLSerializer;
import client.ClientSide;
import server.ServerCommand;
import special.Constants;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public class UpdateCommand implements Command {
    public UpdateCommand() {
        CommandExecutor.addCommand("update", this);

    }

    @Override
    public void execute(String arg, MusicBandsData data) {

        try {
            MusicBand musicBand = EnterElementData.createMusicBand();
            String[] commandParams = new String[2];
            commandParams[0] = arg;
            commandParams[1] = MusicBandsDataXMLSerializer.serializeMusicBand(musicBand);
            ServerCommand serverCommand = new ServerCommand("update", commandParams);
            serverCommand.setUserLogin(Constants.getUserLogin());
            serverCommand.setUserPassword(Constants.getUserPassword());
            String message = serverCommand.serializeToString();
            String received = ClientSide.sendMessage(message);
            System.out.println(received);
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
            System.out.println("Can't connect to server, try to enter command again");
        }


    }
}
