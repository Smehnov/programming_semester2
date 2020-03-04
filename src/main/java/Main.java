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
        boolean isWorking = true;


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

        while (true) {
            System.out.println("Enter you action, use >help to get the list of all commands");
            String action = Inputting.readLine();
            CommandExecutor.execute(action, musicBandsData);
            System.out.println();
        }
//
//        while (!isWorking) {
//            Scanner sc = new Scanner(System.in);
//            System.out.println("Choose your action:");
//            String action = sc.nextLine().replace("\n", "");
//
//            //TODO regex for custom values( id / {element})
//            switch (action) {
//                case "help":
//                    showHelp();
//                    break;
//                case "info":
//                    //TODO info
//                    showInfo(musicBandsData);
//                    break;
//                case "show":
//                    show(musicBandsData.getQueue());
//                    //TODO show
//                    break;
//                case "add":
//                    //TODO add {element}
//                    addElement(musicBandsData);
//                    break;
//                case "update id {element}":
//                    //TODO update id {element}
//                    break;
//                case "remove_by_id id":
//                    //TODO remove_by_id id
//                    break;
//                case "clear":
//                    //TODO clear
//                    break;
//                case "save":
//                    saveToFile(musicBandsData, dataPath);
//                    break;
//                case "execute_script file_name":
//                    //TODO execute_script file_name
//                    break;
//                case "exit":
//                    System.out.println("Exit...");
//                    isWorking = false;
//                    break;
//                case "add_if_max {element}":
//                    //TODO add_if_max {element}
//                    break;
//                case "add_if_min {element}":
//                    //TODO add_if_min {element}
//                    break;
//                case "remove_greater {element}":
//                    //TODO remove_greater {element}
//                    break;
//                case "sum_of_number_of_participants":
//                    //TODO sum_of_number_of_participants
//                    break;
//                case "filter_by_number_of_participants numberOfParticipants":
//                    //TODO filter_by_number_of_participants numberOfParticipants
//                    break;
//                case "filter_contains_name name":
//                    //TODO filter_contains_name name
//                    break;
//                default:
//                    if (Pattern.matches("update \\d+", action)) {
//                        Long id = Long.parseLong(action.split(" ")[1]);
//                        if (musicBandsData.getListOfIds().contains(id)) {
//                            System.out.println("Updating element with id " + id + "...");
//                            MusicBand musicBand = inputtingMusingBand();
//                            musicBandsData.updateMusicBand(id, musicBand);
//                            MusicBand newMusicBand = musicBandsData.getElementById(id);
//                            if (newMusicBand != null) {
//                                System.out.println("Element with id " + id + " was updated\n new element: \n" + newMusicBand);
//
//                            } else {
//                                System.out.println("Error while updating element");
//                            }
//                        } else {
//                            System.out.println("Element with id " + id + " doesn't exist");
//                        }
//
//                    } else {
//                        System.out.println("Command is wrong\n");
//                    }
//
//                    break;
//            }
//
//        }
    }

}
