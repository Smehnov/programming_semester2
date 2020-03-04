import javax.xml.bind.JAXBException;
/**
 * Class for 'save' command
 * @implements Command
 */
public class SaveCommand implements Command {
    public SaveCommand() {
        CommandExecutor.addCommand("save", this);
    }

    @Override
    public void execute(String arg, MusicBandsData data) {

        final String nameOfEnvVar = "LAB_DATA_PATH";
        String dataPath = System.getenv(nameOfEnvVar);

        if (dataPath == null) {
            System.out.println("ERROR\nYou need to set environment variable(LAB_DATA_PATH) with path to you data file(.xml)");
        } else {
            try {
                FileManager.saveToXml(data, dataPath);
                System.out.println("Data saved to file: " + dataPath);
            } catch (JAXBException e) {
                e.printStackTrace();
                System.out.println("Error while saving");
            }
        }
    }
}
