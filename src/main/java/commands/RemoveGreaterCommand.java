package commands;

import band_data.EnterElementData;
import band_data.MusicBand;
import band_data.MusicBandsData;

import java.util.Queue;

public class RemoveGreaterCommand implements Command {
    public RemoveGreaterCommand() {
        CommandExecutor.addCommand("remove_greater", this);
    }

    @Override
    public void execute(String arg, MusicBandsData data) {
        long oldSize = data.getListOfIds().size();

        System.out.println("Enter element data");
        MusicBand musicBand = EnterElementData.createMusicBand();
        data.remove(data.getQueue().stream()
                .filter(o ->musicBand.compareTo(o)>0)
                .findFirst()
                .get());
        //data.removeIfGreater(musicBand);

        long newSize = data.getListOfIds().size();
        System.out.println((oldSize - newSize) + " elements greater than " + musicBand + "\nwere removed");
    }
}
