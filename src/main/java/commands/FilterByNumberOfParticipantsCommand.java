package commands;

import band_data.MusicBand;
import band_data.MusicBandsData;
import client.ClientSide;
import server.ServerCommand;

import java.io.IOException;

public class FilterByNumberOfParticipantsCommand implements Command {
    public FilterByNumberOfParticipantsCommand() {
        CommandExecutor.addCommand("filter_by_number_of_participants", this);
    }

    @Override
    public void execute(String arg, MusicBandsData data) {
            try {
                String[] commandParams = new String[1];
                commandParams[0] = arg;
                ServerCommand serverCommand = new ServerCommand("filter_by_number_of_participants", commandParams);
                String message = serverCommand.serializeToString();

                String received = ClientSide.sendMessage(message);
                System.out.println(received);

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Can't connect to server, try to enter command again");
            }
    }
}

