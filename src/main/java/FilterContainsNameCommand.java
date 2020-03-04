/**
 * Class for 'filter_contains_name' command
 * @implements Command
 */
public class FilterContainsNameCommand implements Command {
    public FilterContainsNameCommand() {
        CommandExecutor.addCommand("filter_contains_name", this);
    }

    @Override
    public void execute(String arg, MusicBandsData data) {
        if (arg != null) {
            System.out.println("Bands that contain " + arg + " in name:");
            for (MusicBand band :
                    data.getQueue()) {
                if (band.getName().contains(arg)) {
                    System.out.println(band);
                }
            }
        } else {
            System.out.println("Wrong input format");
        }
    }
}
