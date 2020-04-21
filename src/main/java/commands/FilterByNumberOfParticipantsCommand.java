package commands;

import band_data.MusicBand;
import band_data.MusicBandsData;

public class FilterByNumberOfParticipantsCommand implements Command {
    public FilterByNumberOfParticipantsCommand() {
        CommandExecutor.addCommand("filter_by_number_of_participants", this);
    }

    @Override
    public void execute(String arg, MusicBandsData data) {
        try {
            Integer numberOfParticipants = Integer.parseInt(arg);
            System.out.println("Elements with number of participants equals " + numberOfParticipants + " :");
            data.getQueue().stream()
                    .filter(o -> o.getNumberOfParticipants()== numberOfParticipants)
                    .forEach(o -> System.out.println(o));
//            for (MusicBand band :
//                    data.getQueue()) {
//                if (band.getNumberOfParticipants() == numberOfParticipants) {
//                    System.out.println(band);
//                }
//            }
        } catch (NumberFormatException e) {
            System.out.println("Wrong number format");
        }
    }
}