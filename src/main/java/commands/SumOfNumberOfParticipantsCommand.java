package commands;

import band_data.MusicBandsData;
import client.ClientSide;
import server.ServerCommand;

import java.io.IOException;

public class SumOfNumberOfParticipantsCommand implements Command {
    public SumOfNumberOfParticipantsCommand() {
        CommandExecutor.addCommand("sum_of_number_of_participants", this);
    }

    @Override
    public void execute(String arg, MusicBandsData data) {
        try {
            String[] commandParams = null;

            ServerCommand serverCommand = new ServerCommand("sum_of_number_of_participants", commandParams);
            String message = serverCommand.serializeToString();

            String received = ClientSide.sendMessage(message);
            System.out.println(received);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Can't connect to server, try to enter command again");
        }
//        System.out.println("Total participants in all bands: " + data.getQueue().stream()
//                .mapToInt(o -> o.getNumberOfParticipants()).sum());
       // System.out.println("Total participants in all bands: " + data.getSumOfNumberOfParticipants());
    }
}
