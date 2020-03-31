package commands;

import band_data.MusicBandsData;

import java.util.HashMap;
import java.util.Map;

public class CommandExecutor {
    private static Map<String, Command> commands = new HashMap<>();

    private static Map<String, Command> getCommands() {
        return commands;
    }

    public static void addCommand(String commandName, Command command) {
        commands.put(commandName, command);
    }


    public static void execute(String action, MusicBandsData musicBandsData) {
        String[] actionParts = action.split(" ");
        if (action.isEmpty()) {
            return;
        }
        if (actionParts.length == 1) {
            Command command = commands.get(actionParts[0]);
            if (command != null) {
                command.execute(null, musicBandsData);
            } else {
                System.out.println("commands.Command doesn't exist. Enter >help for getting more info");
            }
        } else if (actionParts.length == 2) {
            Command command = commands.get(actionParts[0]);
            String arg = actionParts[1];
            if (command != null) {
                command.execute(arg, musicBandsData);
            } else {
                System.out.println("commands.Command doesn't exist. Enter >help for getting more info");
            }
        } else {
            System.out.println("Wrong command input. Enter >help for getting more info");
        }
    }
}
