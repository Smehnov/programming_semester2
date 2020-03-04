public class RemoveByIdCommand implements Command {
    public RemoveByIdCommand() {
        CommandExecutor.addCommand("remove_by_id", this);
    }

    @Override
    public void execute(String arg, MusicBandsData data) {
        try {
            Long id = Long.parseLong(arg);
            if (data.getListOfIds().contains(id)) {
                data.removeMusicBandById(id);
                System.out.println("Band with id " + id + " was removed");
            } else {
                System.out.println("Band with id " + id + " not found");
            }


        } catch (NumberFormatException e) {
            System.out.println("Id's number format error, check >help");
        }
    }
}
