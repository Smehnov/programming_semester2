/**
 * Class for 'remove_greater' command
 * @implements Command
 */
public class RemoveGreaterCommand implements Command {
    public RemoveGreaterCommand() {
        CommandExecutor.addCommand("remove_greater", this);
    }

    @Override
    public void execute(String arg, MusicBandsData data) {
        long oldSize = data.getListOfIds().size();

        System.out.println("Enter element data");
        MusicBand musicBand = EnterElementData.createMusicBand();
        data.removeIfGreater(musicBand);

        long newSize = data.getListOfIds().size();
        System.out.println((oldSize - newSize) + " elements greater then " + musicBand + "\nwere removed");
    }
}
