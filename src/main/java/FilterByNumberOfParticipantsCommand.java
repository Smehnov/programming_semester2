/**
 * Class for command 'filter_by_number_of_participants'
 * @implements Command
 */
public class FilterByNumberOfParticipantsCommand implements Command {
    public FilterByNumberOfParticipantsCommand() {
        CommandExecutor.addCommand("filter_by_number_of_participants", this);
    }

    @Override
    public void execute(String arg, MusicBandsData data) {
        try {
            Integer numberOfParticipants = Integer.parseInt(arg);
            System.out.println("Elements with number of participants equals " + numberOfParticipants + " :");
            for (MusicBand band :
                    data.getQueue()) {
                if (band.getNumberOfParticipants() == numberOfParticipants) {
                    System.out.println(band);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Wrong number format");
        }
    }
}
