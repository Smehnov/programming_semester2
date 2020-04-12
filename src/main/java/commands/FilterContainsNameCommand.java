package commands;

import band_data.MusicBand;
import band_data.MusicBandsData;

public class FilterContainsNameCommand implements Command {
    public FilterContainsNameCommand() {
        CommandExecutor.addCommand("filter_contains_name", this);
    }

    @Override
    public void execute(String arg, MusicBandsData data) {
        if (arg != null) {
            System.out.println("Bands that contain " + arg + " in name:");
            data.getQueue().stream()
                    .filter(o -> o.getName().contains(arg))
                    .forEach(o -> System.out.println(o));
//            for (MusicBand band :
//                    data.getQueue()) {
//                if (band.getName().contains(arg)) {
//                    System.out.println(band);
//                }
//            }
        } else {
            System.out.println("Wrong input format");
        }
    }
}
