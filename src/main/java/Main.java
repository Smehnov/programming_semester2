import band_data.MusicBandsDataXMLSerializer;
import band_data.Inputting;
import band_data.MusicBandsData;
import commands.*;
import server.ServerSide;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    /**
     * Entry point of whole program
     *
     * @param args - useless args in program executing
     */
    public static void main(String[] args) {
        //export LAB_DATA_PATH="data.xml"
        Scanner sc = new Scanner(System.in);
        System.out.print("Choose mode:\nclient\nserver\n>");
        String mode = sc.nextLine().trim();
        if (mode.toLowerCase().equals("server")){
            try {
                ServerSide.runServerSide();
            }catch (IOException e){
                System.out.println("Can't start server, check all params");
                System.exit(0);
            }
        }else if (mode.toLowerCase().equals("client")){

        }else{
            System.out.println("Not known mode, exit...");
            System.exit(0);
        }


        final String nameOfEnvVar = "LAB_DATA_PATH";
        String dataPath = System.getenv(nameOfEnvVar);

        //Comparator for music bands via creation date

        MusicBandsData musicBandsData = new MusicBandsData();

        //Scanner sc = new Scanner(System.in);






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
