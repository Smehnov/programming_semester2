package commands;

import band_data.EnterElementData;
import band_data.MusicBand;
import band_data.MusicBandsData;
import band_data.MusicBandsDataXMLSerializer;
import client.ClientSide;
import commands.Command;
import commands.CommandExecutor;
import server.ServerCommand;

import java.io.IOException;

/**
 * Class for 'remove_by_id' command
 * @implements Command
 */
public class RemoveByIdCommand implements Command {
    public RemoveByIdCommand() {
        CommandExecutor.addCommand("remove_by_id", this);
    }

    @Override
    public void execute(String arg, MusicBandsData data) {
            try {
                String[] commandParams = new String[1];
                commandParams[0] = arg;
                ServerCommand serverCommand = new ServerCommand("remove_by_id", commandParams);
                String message = serverCommand.serializeToString();

                String received = ClientSide.sendMessage(message);
                System.out.println(received);

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Can't connect to server, try to enter command again");
            }
        }
    }