public class UpdateCommand implements Command {
    public UpdateCommand() {
        CommandExecutor.addCommand("update", this);

    }

    @Override
    public void execute(String arg, MusicBandsData data) {

        try {
            Long id = Long.parseLong(arg);
            MusicBand musicBand = EnterElementData.createMusicBand();
            if (data.getListOfIds().contains(id)) {
                data.updateMusicBand(id, musicBand);
                MusicBand newMusicBand = data.getElementById(id);
                System.out.println("Element with id " + id + " was updated, new one:\n" + newMusicBand);
            } else {
                System.out.println("Band with id " + id + " doesn't exist");
            }

        } catch (NumberFormatException e) {
            System.out.println("Wrong id number format");
        }


    }
}
