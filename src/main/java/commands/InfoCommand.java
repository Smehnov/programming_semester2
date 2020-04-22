package commands;

import band_data.MusicBandsData;
import client.ClientSide;
import server.ServerCommand;

import java.io.IOException;
import java.net.SocketTimeoutException;

public class InfoCommand implements Command {
    public InfoCommand() {
        CommandExecutor.addCommand("info", this);
    }

    public void execute(String arg, MusicBandsData data) {
        boolean commandDone = false;

            try {
                ServerCommand serverCommand = new ServerCommand("info", new String[0]);
                String message = serverCommand.serializeToString();

                String received = ClientSide.sendMessage(message);

                System.out.println(received);
            } catch (IOException e) {
                System.out.println("Error while sending message to server...");
            }
    }
}
