package commands;

import band_data.MusicBandsData;
import band_data.MusicBandsDataXMLSerializer;
import client.ClientSide;
import server.ServerCommand;
import special.Constants;

import java.io.IOException;

public class ClearCommand implements Command {
    public ClearCommand() {
        CommandExecutor.addCommand("clear", this);
    }

    @Override
    public void execute(String arg, MusicBandsData data) {
//        data.clearCollection();
//        System.out.println("Collection was cleared");
        try {
            String[] commandParams = null;

            ServerCommand serverCommand = new ServerCommand("clear", commandParams);
            serverCommand.setUserLogin(Constants.getUserLogin());
            serverCommand.setUserPassword(Constants.getUserPassword());
            String message = serverCommand.serializeToString();

            String received = ClientSide.sendMessage(message);
            System.out.println(received);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Can't connect to server, try to enter command again");
        }
    }
}
