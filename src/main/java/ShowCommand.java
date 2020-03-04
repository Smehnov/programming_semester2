public class ShowCommand implements Command {
    public ShowCommand() {
        CommandExecutor.addCommand("show", this);
    }

    @Override
    public void execute(String arg, MusicBandsData data) {
        System.out.println("Queue elements: ");

        for (MusicBand band : data.getQueue()) {
            System.out.println(band.toString());
        }
    }
}
