package commands;

import band_data.MusicBandsData;

public interface Command {
    void execute(String arg, MusicBandsData data);
}
