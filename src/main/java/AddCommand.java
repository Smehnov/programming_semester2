public class AddCommand implements Command {
    public AddCommand() {
        CommandExecutor.addCommand("add", this);
    }

    public void execute(String args, MusicBandsData data) {
        MusicBand musicBand = EnterElementData.createMusicBand();

        data.addMusicBand(musicBand);
        System.out.println("New element was added:\n" + musicBand.toString());

    }
}
