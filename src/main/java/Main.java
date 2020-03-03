import java.time.ZonedDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
        Comparator<MusicBand> musicBandComparator = new Comparator<MusicBand>() {
            @Override
            public int compare(MusicBand B1, MusicBand B2) {
                return B1.getDate().compareTo(B2.getDate());
            }
        };


        //TODO file reading input
        MusicBandsData musicBandsData = new MusicBandsData();

        Scanner sc = new Scanner(System.in);
        boolean isWorking = true;


        if (dataPath == null) {
            System.out.println("ERROR\nYou need to set environment variable(LAB_DATA_PATH) with path to you data file(.xml)");
            isWorking = false;
            System.out.println("Exit...");
        } else {
            System.out.println("Getting data from file " + dataPath + " ...");
            try {
                musicBandsData = FileManager.readFromXML(dataPath);
                System.out.println("Got data");

            } catch (JAXBException e) {
                System.out.println(e.getStackTrace());
                System.out.println("error while reading data from XML");
            }

        }


        while (isWorking) {
            System.out.println("Choose your action:");
            String action = sc.nextLine().replace("\n", "");

            //TODO regex for custom values( id / {element})
            switch (action) {
                case "help":
                    showHelp();
                    break;
                case "info":
                    //TODO info
                    showInfo(musicBandsData);
                    break;
                case "show":
                    show(musicBandsData.getQueue());
                    //TODO show
                    break;
                case "add":
                    //TODO add {element}
                    addElement(musicBandsData);
                    break;
                case "update id {element}":
                    //TODO update id {element}
                    break;
                case "remove_by_id id":
                    //TODO remove_by_id id
                    break;
                case "clear":
                    //TODO clear
                    break;
                case "save":
                    saveToFile(musicBandsData, dataPath);
                    break;
                case "execute_script file_name":
                    //TODO execute_script file_name
                    break;
                case "exit":
                    System.out.println("Exit...");
                    isWorking = false;
                    break;
                case "add_if_max {element}":
                    //TODO add_if_max {element}
                    break;
                case "add_if_min {element}":
                    //TODO add_if_min {element}
                    break;
                case "remove_greater {element}":
                    //TODO remove_greater {element}
                    break;
                case "sum_of_number_of_participants":
                    //TODO sum_of_number_of_participants
                    break;
                case "filter_by_number_of_participants numberOfParticipants":
                    //TODO filter_by_number_of_participants numberOfParticipants
                    break;
                case "filter_contains_name name":
                    //TODO filter_contains_name name
                    break;
                default:
                    if (Pattern.matches("update \\d+", action)) {
                        Long id = Long.parseLong(action.split(" ")[1]);
                        if (musicBandsData.getListOfIds().contains(id)) {
                            System.out.println("Updating element with id " + id + "...");
                            MusicBand musicBand = inputtingMusingBand();
                            musicBandsData.updateMusicBand(id, musicBand);
                            MusicBand newMusicBand = musicBandsData.getElementById(id);
                            if (newMusicBand != null) {
                                System.out.println("Element with id " + id + " was updated\n new element: \n" + newMusicBand);

                            } else {
                                System.out.println("Error while updating element");
                            }
                        } else {
                            System.out.println("Element with id " + id + " doesn't exist");
                        }

                    } else {
                        System.out.println("Command is wrong\n");
                    }

                    break;
            }

        }
    }

    /**
     * Method to show available commands
     */
    static void showHelp() {
        System.out.println("help : вывести справку по доступным командам\n" +
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add : добавить новый элемент в коллекцию\n" +
                "update <id> : обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_by_id <id> : удалить элемент из коллекции по его id\n" +
                "clear : очистить коллекцию\n" +
                "save : сохранить коллекцию в файл\n" +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "exit : завершить программу (без сохранения в файл)\n" +
                "add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции\n" +
                "add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции\n" +
                "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный\n" +
                "sum_of_number_of_participants : вывести сумму значений поля numberOfParticipants для всех элементов коллекции\n" +
                "filter_by_number_of_participants numberOfParticipants : вывести элементы, значение поля numberOfParticipants которых равно заданному\n" +
                "filter_contains_name name : вывести элементы, значение поля name которых содержит заданную подстроку" +
                "Формат ввода команд:\n" +
                "\n" +
                "Все аргументы команды, являющиеся стандартными типами данных (примитивные типы, классы-оболочки, String, классы для хранения дат), должны вводиться в той же строке, что и имя команды.\n" +
                "Все составные типы данных (объекты классов, хранящиеся в коллекции) должны вводиться по одному полю в строку.\n" +
                "При вводе составных типов данных пользователю должно показываться приглашение к вводу, содержащее имя поля (например, \"Введите дату рождения:\")\n" +
                "Если поле является enum'ом, то вводится имя одной из его констант (при этом список констант должен быть предварительно выведен).\n" +
                "При некорректном пользовательском вводе (введена строка, не являющаяся именем константы в enum'е; введена строка вместо числа; введённое число не входит в указанные границы и т.п.) должно быть показано сообщение об ошибке и предложено повторить ввод поля.\n" +
                "Для ввода значений null использовать пустую строку.\n" +
                "Поля с комментарием \"Значение этого поля должно генерироваться автоматически\" не должны вводиться пользователем вручную при добавлении.");
    }

    static void updateElement(MusicBandsData musicBandsData, int id) {
        //musicBandsData.updateElementById(id, musicBand);
    }

    static void addElement(MusicBandsData data) {
        MusicBand musicBand = inputtingMusingBand();
        data.addMusicBand(musicBand);
        System.out.println("New element was added");
    }

    /**
     * Show info about queue
     *
     * @param data
     */
    static void showInfo(MusicBandsData data) {
        System.out.println("Тип: PriorityQueue\n" +
                "Дата инициализации: " + data.getInizializationTime() + '\n' +
                "Количество элементов: " + data.getQueue().size() + '\n'

        );
    }

    /**
     * Method to show info about every element of queue
     *
     * @param queue
     */
    static void show(PriorityQueue<MusicBand> queue) {
        System.out.println("Queue elements: ");

        for (MusicBand band : queue) {

            System.out.println(band.toString());

        }
    }

    static void saveToFile(MusicBandsData musicBandsData, String dataPath) {
        if (dataPath == null) {
            System.out.println("ERROR\nYou need to set environment variable(LAB_DATA_PATH) with path to you data file(.xml)");
        } else {
            try {
                FileManager.saveToXml(musicBandsData, dataPath);
                System.out.println("Data saved to file: " + dataPath);
            } catch (JAXBException e) {
                e.printStackTrace();
                System.out.println("Error while saving");
            }
        }
    }

    static MusicBand inputtingMusingBand() {
        Scanner sc = new Scanner(System.in);
        MusicBand musicBand = new MusicBand();
        System.out.println("Enter name");
        String name = "";
        while (name.equals("")) {
            name = sc.next().trim();
            if (name.equals("")) {
                System.out.println("Field can't be empty");
            }
        }
        System.out.println("Enter coordinate X");
        Integer coordX = sc.nextInt();
        System.out.println("Enter coordinate Y");
        Long coordY = sc.nextLong();

        System.out.println("Enter number of participants");
        Integer numberOfParticipants = 0;
        while (numberOfParticipants <= 0) {
            numberOfParticipants = sc.nextInt();
            if (numberOfParticipants <= 0) {
                System.out.println("Number of participants must be greater then 0");
            }
        }

        MusicGenre genre = null;
        MusicGenre[] possibleGenres = MusicGenre.values();
        String[] possibleGenresStrings = new String[possibleGenres.length];
        for (int i = 0; i < possibleGenres.length; i++) {
            MusicGenre possibleGenre = possibleGenres[i];
            possibleGenresStrings[i] = possibleGenre.name();
        }
        System.out.println("Enter music genre(" + String.join(", ", possibleGenresStrings) + ") or leave it empty for null");
        boolean correctInput = false;
        while (!correctInput) {
            List<String> genresList = Arrays.asList(possibleGenresStrings);

            String genreS = sc.next();
            if (!genresList.contains(genreS) && !genreS.equals("")) {
                System.out.println("Enter genre from the list(" + String.join(", ", possibleGenresStrings) + ") or leave it empty for null");
            } else {
                if (genresList.contains(genreS)) {
                    genre = MusicGenre.valueOf(genreS);
                }
                correctInput = true;
            }
        }

        Album bestAlbum = null;
        System.out.println("Enter the name of the best album(or leave it empty for null");
        String albumNameInput = sc.next().trim();
        if (!albumNameInput.equals("")) {
            System.out.println("Enter best album length");
            Long albumLength = (long) 0;
            while (albumLength <= 0) {
                albumLength = sc.nextLong();
                if (albumLength <= 0) {
                    System.out.println("Album length must be greater then 0");
                }
            }
            bestAlbum = new Album(albumNameInput, albumLength);
        }

        musicBand.setName(name);
        musicBand.setCoordinates(coordX, coordY);
        musicBand.setNumberOfParticipants(numberOfParticipants);
        musicBand.setGenre(genre);
        musicBand.setBestAlbum(bestAlbum);
        return musicBand;
    }

    /**
     * Read String from user input or read from file
     *
     * @param readingFromFile true if it's needed to read from file
     */
    static String readCommand(boolean readingFromFile) {
        String line = "";
        if (readingFromFile) {

        } else {
            Scanner sc = new Scanner(System.in);
            line = sc.nextLine();
        }

        return line;
    }
}
