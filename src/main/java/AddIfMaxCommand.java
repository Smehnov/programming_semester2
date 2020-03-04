
public class AddIfMaxCommand implements Command {
    public AddIfMaxCommand() {
        CommandExecutor.addCommand("add_if_max", this);
    }

    @Override
    public void execute(String arg, MusicBandsData data) {
        if (data.getQueueSize() > 0) {

            MusicBand maxMusicBand = data.getMaxMusicBand();
            System.out.println("Max element:");

            System.out.println(maxMusicBand);
            System.out.println("Entering music band fields...");
            MusicBand musicBand = EnterElementData.createMusicBand();
            if (musicBand.compareTo(maxMusicBand) > 0) {
                data.addMusicBand(musicBand);
                System.out.println("New element added:");
                System.out.println(musicBand);
            } else {
                System.out.println("New element isn't greater then min element");
            }
        } else {
            System.out.println("Collection is empty, use >add command instead");
        }

    }
}
