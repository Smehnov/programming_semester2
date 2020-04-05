import band_data.FileManager;
import band_data.Inputting;
import band_data.MusicBandsData;
import commands.*;

import javax.xml.bind.JAXBException;

public class Main {
    /**
     * Entry point of whole program
     *
     * @param args - useless args in program executing
     */
    public static void main(String[] args) {
        //export LAB_DATA_PATH="data.xml"
        final String nameOfEnvVar = "LAB_DATA_PATH";
        String dataPath = System.getenv(nameOfEnvVar);

        //Comparator for music bands via creation date

        MusicBandsData musicBandsData = new MusicBandsData();

        //Scanner sc = new Scanner(System.in);



        if (dataPath == null) {
            System.out.println("ERROR\nYou need to set environment variable(LAB_DATA_PATH) with path to you data file(.xml)");
            System.out.println("Exit...");
            System.exit(0);
        } else {
            System.out.println("Getting data from file " + dataPath + " ...");
            try {
                musicBandsData = FileManager.readFromXML(dataPath);
                System.out.println("Got data");

            } catch (JAXBException e) {
                e.printStackTrace();
                System.out.println("error while reading data from XML");
                System.out.println("Exit...");
                System.exit(0);
            }

        }


        HelpCommand helpCommand = new HelpCommand();
        InfoCommand infoCommand = new InfoCommand();
        ExitCommand exitCommand = new ExitCommand();
        ShowCommand showCommand = new ShowCommand();
        SaveCommand saveCommand = new SaveCommand();
        AddCommand addCommand = new AddCommand();
        UpdateCommand updateCommand = new UpdateCommand();
        RemoveByIdCommand removeByIdCommand = new RemoveByIdCommand();
        ClearCommand clearCommand = new ClearCommand();
        ExecuteScriptCommand executeScriptCommand = new ExecuteScriptCommand();
        AddIfMinCommand addIfMinCommand = new AddIfMinCommand();
        AddIfMaxCommand addIfMaxCommand = new AddIfMaxCommand();
        RemoveGreaterCommand removeGreaterCommand = new RemoveGreaterCommand();
        SumOfNumberOfParticipantsCommand sumOfNumberOfParticipantsCommand = new SumOfNumberOfParticipantsCommand();
        FilterByNumberOfParticipantsCommand filterByNumberOfParticipantsCommand = new FilterByNumberOfParticipantsCommand();
        FilterContainsNameCommand filterContainsNameCommand = new FilterContainsNameCommand();

        while (true) {
            System.out.println("Enter you action, use >help to get the list of all commands");
            String action = Inputting.readLine();
            if (!action.isEmpty()) {
                CommandExecutor.execute(action, musicBandsData);
                System.out.println();
            }
        }
    }

}
