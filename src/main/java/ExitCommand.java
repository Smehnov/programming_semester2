public class ExitCommand implements Command {
    public ExitCommand(){
        CommandExecutor.addCommand("exit", this);
    }


    @Override
    public void execute(String arg, MusicBandsData data) {
        System.exit(0);
    }
}
