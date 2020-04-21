package commands;

import band_data.MusicBand;
import band_data.MusicBandsData;
import client.ClientSide;
import server.ServerCommand;

import java.io.IOException;

public class ShowCommand implements Command {
    public ShowCommand() {
        CommandExecutor.addCommand("show", this);
    }

    @Override
    public void execute(String arg, MusicBandsData data) {

        try {
            ServerCommand serverCommand = new ServerCommand("show", new String[0]);
            String message = serverCommand.serializeToString();

            String received = ClientSide.sendMessage(message);
            System.out.println(received);
        } catch (IOException e) {
            System.out.println("Error while sending message to server...");
        }

    }
}
