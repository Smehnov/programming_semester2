public class SumOfNumberOfParticipantsCommand implements Command {
    public SumOfNumberOfParticipantsCommand() {
        CommandExecutor.addCommand("sum_of_number_of_participants", this);
    }

    @Override
    public void execute(String arg, MusicBandsData data) {
        System.out.println("Total participants in all bands: " + data.getSumOfNumberOfParticipants());
    }
}
