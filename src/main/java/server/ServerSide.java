package server;

import band_data.MusicBand;
import band_data.MusicBandsData;
import band_data.MusicBandsDataXMLSerializer;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ServerSide {
    final String nameOfEnvVar = "LAB_DATA_PATH";
    MusicBandsData musicBandsData;
    DatagramSocket serverSocket;
    byte[] buf;
    BufferedReader bufferedReader;

    ServerSide(int port) throws IOException {
        serverSocket = new DatagramSocket(port);
        serverSocket.setSoTimeout(100);

        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        readMusicBand();

    }

    void readMusicBand() {
        musicBandsData = new MusicBandsData();
        String dataPath = System.getenv(nameOfEnvVar);

        if (dataPath == null) {
            System.out.println("ERROR\nYou need to set environment variable(LAB_DATA_PATH) with path to you data file(.xml)");
            System.out.println("Exit...");
            System.exit(0);
        } else {
            System.out.println("Getting data from file " + dataPath + " ...");
            try {
                musicBandsData = MusicBandsDataXMLSerializer.readFromXML(dataPath);
                System.out.println("Got data");

            } catch (JAXBException e) {
                e.printStackTrace();
                System.out.println("error while reading data from XML");
                System.out.println("Exit...");
                System.exit(0);
            }

        }
    }

    ServerSide() throws IOException {
        this(666);
    }

    String processCommand(ServerCommand serverCommand) {
        switch (serverCommand.getType()) {
            case "add":
                try {
                    MusicBand musicBand = MusicBandsDataXMLSerializer.readMusicBandFromXMLString(serverCommand.getParams()[0]);
                    musicBandsData.addMusicBand(musicBand);
                    String msg = "New element was added:\n" + musicBand.toString();

                    return msg;
                } catch (JAXBException e) {
                    return "Error while extracting music band data on server...";
                }
            case "show":
                String msg = "";
                msg+= "Queue elements: \n";


                for (MusicBand band : musicBandsData.getQueue()) {
                    msg+=band.toString() + "\n";
                }
                return msg;
            case "info":

                return("Тип: PriorityQueue\n" +
                        "Дата инициализации: " + musicBandsData.getInizializationTime() + '\n' +
                        "Количество элементов: " + musicBandsData.getQueue().size() + '\n'
                );
            case "help":

                return ("help : вывести справку по доступным командам\n" +
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
                        "filter_contains_name name : вывести элементы, значение поля name которых содержит заданную подстроку");
            case "clear":
                musicBandsData.getQueue().clear();
                return ("Collection was cleared");
            case "sum_of_number_of_participants":
                return("Total participants in all bands: " + musicBandsData.getQueue().stream()
                        .mapToInt(o -> o.getNumberOfParticipants()).sum());

        }



        return "UNKNOWN TYPE OF COMMAND";
    }

    void saveToFile() {
        String dataPath = System.getenv(nameOfEnvVar);
        System.out.println("Saving to file ...");
        if (dataPath == null) {
            System.out.println("ERROR\nYou need to set environment variable(LAB_DATA_PATH) with path to you data file(.xml)");
        } else {
            try {
                MusicBandsDataXMLSerializer.saveToXml(musicBandsData, dataPath);
                System.out.println("Data saved to file: " + dataPath);
            } catch (JAXBException e) {
                e.printStackTrace();
                System.out.println("Error while saving");
            }
        }

    }

    void processInput(String s) {
        switch (s) {
            case "exit":
                saveToFile();
                System.out.println("EXIT...");
                System.exit(0);
                break;
            case "save":
                saveToFile();
                break;
        }
    }

    void run() {
        boolean running = true;
        try {

            while (running) {
                buf = new byte[65536];
                DatagramPacket packet
                        = new DatagramPacket(buf, buf.length);

                try {
                    serverSocket.receive(packet);
                } catch (SocketTimeoutException e) {
                    if (bufferedReader.ready()) {
                        processInput(bufferedReader.readLine());
                    }

                    continue;
                }

                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Received " + received);
                if (received.equals("end")) {
                    running = false;
                    continue;
                }

                String answer = "";
                try {
                    ServerCommand serverCommand = ServerCommand.deserializeFromString(received);
                    answer = processCommand(serverCommand);
                } catch (ClassNotFoundException e) {
                    //WRONG COMMAND
                }

                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                byte[] answerBuf = answer.getBytes();
                packet = new DatagramPacket(answerBuf, answerBuf.length, address, port);


                serverSocket.send(packet);
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Can't start server because of io exception");
        }
    }

    public static void runServerSide() throws IOException {
        ServerSide serverSide = new ServerSide();
        serverSide.run();
    }
}
