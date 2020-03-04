public class ClearCommand implements Command {
    public ClearCommand() {
        CommandExecutor.addCommand("clear", this);
    }

    @Override
    public void execute(String arg, MusicBandsData data) {
        data.clearCollection();
        System.out.println("Collection was cleared");
    }
}
