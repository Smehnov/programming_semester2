package commands;

import band_data.MusicBand;
import band_data.MusicBandsData;
import commands.Command;
import commands.CommandExecutor;

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
            Long id = Long.parseLong(arg);
            if (data.getListOfIds().contains(id)) {
                data.remove( data.getQueue().stream()
                        .filter(o ->o.getId()==id)
                        .findFirst()
                        .get());
                //data.removeMusicBandById(id);
                System.out.println("Band with id " + id + " was removed");
            } else {
                System.out.println("Band with id " + id + " not found");
            }


        } catch (NumberFormatException e) {
            System.out.println("Id's number format error, check >help");
        }
    }
}
