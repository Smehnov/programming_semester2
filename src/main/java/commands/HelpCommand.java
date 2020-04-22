package commands;

import band_data.MusicBandsData;
import band_data.MusicBandsDataXMLSerializer;
import client.ClientSide;
import server.ServerCommand;

import java.io.IOException;

public class HelpCommand implements Command {

    public HelpCommand() {
        CommandExecutor.addCommand("help", this);
    }

    public void execute(String arg, MusicBandsData data) {
        try {
            String[] commandParams = null;

            ServerCommand serverCommand = new ServerCommand("help", commandParams);
            String message = serverCommand.serializeToString();

            String received = ClientSide.sendMessage(message);
            System.out.println(received);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Can't connect to server, try to enter command again");
        }
    }
}

