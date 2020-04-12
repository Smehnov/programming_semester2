package commands;

import band_data.MusicBandsData;

public class SumOfNumberOfParticipantsCommand implements Command {
    public SumOfNumberOfParticipantsCommand() {
        CommandExecutor.addCommand("sum_of_number_of_participants", this);
    }

    @Override
    public void execute(String arg, MusicBandsData data) {
        System.out.println("Total participants in all bands: " + data.getQueue().stream()
                .mapToInt(o -> o.getNumberOfParticipants()).sum());
       // System.out.println("Total participants in all bands: " + data.getSumOfNumberOfParticipants());
    }
}
