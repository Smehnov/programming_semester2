package commands;

import band_data.EnterElementData;
import band_data.MusicBand;
import band_data.MusicBandsData;

public class AddIfMinCommand implements Command {
    public AddIfMinCommand() {
        CommandExecutor.addCommand("add_if_min", this);
    }

    @Override
    public void execute(String arg, MusicBandsData data) {
        if (data.getQueueSize() > 0) {

            MusicBand minMusicBand = data.getMinMusicBand();
            System.out.println("Min element:");
            System.out.println(minMusicBand);

            System.out.println("Entering music band fields...");
            MusicBand musicBand = EnterElementData.createMusicBand();

            if (musicBand.compareTo(minMusicBand) < 0) {
                data.addMusicBand(musicBand);
                System.out.println("New element added:");
                System.out.println(musicBand);
            } else {
                System.out.println("New element isn't less then min element");
            }
        } else {
            System.out.println("Collection is empty, use >add command instead");
        }

    }
}
