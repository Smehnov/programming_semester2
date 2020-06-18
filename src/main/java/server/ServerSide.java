package server;

import band_data.MusicBand;
import band_data.MusicBandsData;
import band_data.MusicBandsDataXMLSerializer;
import org.apache.commons.codec.digest.DigestUtils;
import special.Constants;
import special.DataBase;
import special.UserInfo;

import javax.xml.bind.JAXBException;
import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerSide {
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


    }

    ServerSide() throws IOException {
        this(Constants.getPort());
    }

    Object processCommand(ServerCommand serverCommand) {
        String userLogin = serverCommand.getUserLogin();
        String userPassword = serverCommand.getUserPassword();

        String passwordHash = DigestUtils.sha1Hex(userPassword);
        UserInfo userInfo = DataBase.getUser(userLogin);

        if (serverCommand.getType().equals("register")) {
            if (userInfo != null) {
                return "This username already used";
            } else {
                DataBase.createUser(userLogin, passwordHash);
                return "You were registred";
            }
        }
        if (serverCommand.getType().equals("login")) {
            if (userInfo == null) {
                return "This user doesn't exist";
            } else {
                if (passwordHash.equals(userInfo.getPassword())) {
                    return "Userdata is ok";
                } else {
                    return "Password invalid";
                }
            }
        }
        System.out.println(userLogin);
        if (!passwordHash.equals(userInfo.getPassword())) {
            return "Password invalid";
        }

        System.out.println(serverCommand.getType());
        switch (serverCommand.getType()) {
            case "add":
                return add(serverCommand, userInfo);
            case "show":
                return show(serverCommand, userInfo);
            case "info":
                return info(serverCommand, userInfo);
            case "help":
                return help(serverCommand, userInfo);
            case "clear":
                return clear(serverCommand, userInfo);
            case "sum_of_number_of_participants":
                return sumOfNumberOfParticipants(serverCommand, userInfo);
            case "remove_by_id":
                return removeById(serverCommand, userInfo);
            case "add_if_max":
                return addIfMax(serverCommand, userInfo);
            case "add_if_min":
                return addIfMin(serverCommand, userInfo);
            case "filter_by_number_of_participants":
                return filterByNumberOfParticipants(serverCommand, userInfo);
            case "filter_contains_name":
                return filterContainsName(serverCommand, userInfo);
            case "update":
                return update(serverCommand, userInfo);
            case "remove_greater":
                return removeGreater(serverCommand, userInfo);
        }


        return "UNKNOWN TYPE OF COMMAND";
    }

    String clear(ServerCommand serverCommand, UserInfo userInfo){
        MusicBandsData musicBandsData1 = DataBase.getMusicBandsDataByUserId(userInfo.getId());
        musicBandsData1.clearCollection();
        DataBase.saveMusicBandsDataForUserWithId(userInfo.getId(), musicBandsData1);


        return ("Collection was cleared");



    }

    String removeGreater(ServerCommand serverCommand, UserInfo userInfo) {
        MusicBandsData musicBandsData1 = DataBase.getMusicBandsDataByUserId(userInfo.getId());


        long oldSize = musicBandsData1.getListOfIds().size();
        try {
            MusicBand musicBand = MusicBandsDataXMLSerializer.readMusicBandFromXMLString(serverCommand.getParams()[0]);
//                musicBandsData.remove(musicBandsData.getQueue().stream()
//                        .filter(o ->musicBand.compareTo(o)>0)
//                        .findFirst()
//                        .get());
            String msg2 = "";
            for (Object k : musicBandsData1.getQueue().stream().filter(o -> musicBand.compareTo(o) > 0).toArray()) {
                musicBandsData1.remove((MusicBand) k);
                msg2 += ((MusicBand) k).toString();
            }
            long newSize = musicBandsData1.getListOfIds().size();
            msg2 += ((oldSize - newSize) + " elements greater than " + musicBand + "\nwere removed");
            DataBase.saveMusicBandsDataForUserWithId(userInfo.getId(), musicBandsData1);

            return msg2;
        } catch (JAXBException e) {
            return "Can't parse sent band";
        }
    }

    String update(ServerCommand serverCommand, UserInfo userInfo) {
        MusicBandsData musicBandsData1 = DataBase.getMusicBandsDataByUserId(userInfo.getId());


        try {
            long id = Long.parseLong(serverCommand.getParams()[0]);
            try {
                MusicBand musicBand = MusicBandsDataXMLSerializer.readMusicBandFromXMLString(serverCommand.getParams()[1]);

                if (musicBandsData1.getListOfIds().contains(id)) {
                    musicBandsData1.updateMusicBand(id, musicBand);
                    MusicBand newMusicBand = musicBandsData1.getElementById(id);
                    DataBase.saveMusicBandsDataForUserWithId(userInfo.getId(), musicBandsData1);

                    return ("Element with id " + id + " was updated, new one:\n" + newMusicBand);
                } else {
                    return ("Band with id " + id + " doesn't exist");
                }
            } catch (JAXBException e) {
                return ("Can't parse sent band");
            }
        } catch (NumberFormatException e) {
            return ("Wrong id number format");
        }
    }

    String filterContainsName(ServerCommand serverCommand, UserInfo userInfo) {
        MusicBandsData musicBandsData1 = DataBase.getMusicBandsDataByUserId(userInfo.getId());

        String msg1 = "";
        String arg = serverCommand.getParams()[0];
        if (arg != null) {
            msg1 += "Bands that contain " + arg + " in name:";
//                    data.getQueue().stream()
//                            .filter(o -> o.getName().contains(arg))
//                            .forEach(o -> System.out.println(o));
            for (MusicBand band :
                    musicBandsData1.getQueue()) {
                if (band.getName().contains(arg)) {
                    msg1 += band;
                }
            }
            return msg1;

        } else {
            return ("Wrong input format");
        }
    }

    String filterByNumberOfParticipants(ServerCommand serverCommand, UserInfo userInfo) {
        MusicBandsData musicBandsData1 = DataBase.getMusicBandsDataByUserId(userInfo.getId());

        try {
            String msg1 = "";
            Integer numberOfParticipants = Integer.parseInt(serverCommand.getParams()[0]);
            msg1 += "Elements with number of participants equals " + numberOfParticipants + ":";
            for (MusicBand band :
                    musicBandsData1.getQueue()) {
                if (band.getNumberOfParticipants() == numberOfParticipants) {
                    msg1 += band;
                }
            }
            return msg1;
        } catch (NumberFormatException e) {
            return ("Wrong number format");
        }
    }

    String addIfMin(ServerCommand serverCommand, UserInfo userInfo) {
        MusicBandsData musicBandsData1 = DataBase.getMusicBandsDataByUserId(userInfo.getId());

        if (musicBandsData1.getQueueSize() > 0) {

            MusicBand minMusicBand = musicBandsData1.getQueue().stream().max((p1, p2) -> p1.compareTo(p2)).get();
            try {
                MusicBand musicBand = MusicBandsDataXMLSerializer.readMusicBandFromXMLString(serverCommand.getParams()[0]);

                if (musicBand.compareTo(minMusicBand) < 0) {
                    musicBandsData1.addMusicBand(musicBand);
                    DataBase.saveMusicBandsDataForUserWithId(userInfo.getId(), musicBandsData1);


                    return ("New element added:" + musicBand);
                } else {
                    return ("New element isn't less then min element");
                }
            } catch (JAXBException e) {
                return "Can't parse sent band";
            }
        } else {
            return ("Collection is empty, use >add command instead");
        }
    }

    String addIfMax(ServerCommand serverCommand, UserInfo userInfo) {
        MusicBandsData musicBandsData1 = DataBase.getMusicBandsDataByUserId(userInfo.getId());

        if (musicBandsData1.getQueueSize() > 0) {
            MusicBand maxMusicBand = musicBandsData1.getQueue().stream().max((p1, p2) -> p1.compareTo(p2)).get();
            try {
                MusicBand musicBand = MusicBandsDataXMLSerializer.readMusicBandFromXMLString(serverCommand.getParams()[0]);
                if (musicBand.compareTo(maxMusicBand) > 0) {
                    musicBandsData1.addMusicBand(musicBand);
                    DataBase.saveMusicBandsDataForUserWithId(userInfo.getId(), musicBandsData1);

                    return ("New element added:" + musicBand);

                } else {
                    return ("New element isn't greater than max element");
                }
            } catch (JAXBException e) {
                return "Can't parse sent band";
            }

        } else {
            return ("Collection is empty, use >add command instead");
        }
    }

    String removeById(ServerCommand serverCommand, UserInfo userInfo) {
        MusicBandsData musicBandsData1 = DataBase.getMusicBandsDataByUserId(userInfo.getId());

        try {
            Long id = Long.parseLong(serverCommand.getParams()[0]);
            if (musicBandsData1.getListOfIds().contains(id)) {
                musicBandsData1.remove(musicBandsData1.getQueue().stream()
                        .filter(o -> o.getId() == id)
                        .findFirst()
                        .get());
                DataBase.saveMusicBandsDataForUserWithId(userInfo.getId(), musicBandsData1);

                return ("Band was removed");
            } else {
                return ("Band not found");
            }


        } catch (NumberFormatException e) {
            return ("Id's number format error, check >help");
        }
    }

    String add(ServerCommand serverCommand, UserInfo userInfo) {
        try {
            MusicBandsData musicBandsData1 = DataBase.getMusicBandsDataByUserId(userInfo.getId());
            MusicBand musicBand = MusicBandsDataXMLSerializer.readMusicBandFromXMLString(serverCommand.getParams()[0]);

            musicBandsData1.addMusicBand(musicBand);
            DataBase.saveMusicBandsDataForUserWithId(userInfo.getId(), musicBandsData1);
            String msg = "New element was added!";
            return msg;
        } catch (JAXBException e) {
            e.printStackTrace();
            return "Error while extracting music band data on server...";

        }
    }

    String help(ServerCommand serverCommand, UserInfo userInfo) {
        return (
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                        "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                        "add : добавить новый элемент в коллекцию\n" +
                        "update <id> : обновить значение элемента коллекции, id которого равен заданному\n" +
                        "remove_by_id <id> : удалить элемент из коллекции по его id\n" +
                        "clear : очистить коллекцию\n" +
                        "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                        "exit : завершить работу клиента\n" +
                        "add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции\n" +
                        "add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции\n" +
                        "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный\n" +
                        "sum_of_number_of_participants : вывести сумму значений поля numberOfParticipants для всех элементов коллекции\n" +
                        "filter_by_number_of_participants numberOfParticipants : вывести элементы, значение поля numberOfParticipants которых равно заданному\n" +
                        "filter_contains_name name : вывести элементы, значение поля name которых содержит заданную подстроку");

    }

    ArrayList<MusicBand> show(ServerCommand serverCommand, UserInfo userInfo) {
        String msg = "";
        msg += "Queue elements: \n";
        ArrayList<MusicBand> bands = new ArrayList<>();
        MusicBandsData musicBandsData1 = DataBase.getMusicBandsDataByUserId(userInfo.getId());
        for (MusicBand band : musicBandsData1.getQueue()) {
            bands.add(band);
            msg += band.toString() + "\n";
        }
        return bands;
    }

    String info(ServerCommand serverCommand, UserInfo userInfo) {
        MusicBandsData musicBandsData1 = DataBase.getMusicBandsDataByUserId(userInfo.getId());
        return ("Тип: PriorityQueue\n" +
                "Количество элементов: " + musicBandsData1.getQueue().size() + '\n'
        );
    }

    String sumOfNumberOfParticipants(ServerCommand serverCommand, UserInfo userInfo) {
        MusicBandsData musicBandsData1 = DataBase.getMusicBandsDataByUserId(userInfo.getId());
        return ("Total participants in all bands: " + musicBandsData1.getQueue().stream()
                .mapToInt(o -> o.getNumberOfParticipants()).sum());
    }

    void saveToFile() {


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
        System.out.print(">");
    }

    class RunnableHandler implements Runnable {
        private final ServerAnswer input;
        private final ExecutorService executorService;

        public RunnableHandler(ServerAnswer input, ExecutorService executorService) {
            this.input = input;
            this.executorService = executorService;
        }

        public void run() {
            try {
                ServerCommand serverCommand = ServerCommand.deserializeFromString((String)input.getMessage());
                Object result = processCommand(serverCommand);



                executorService.submit(new RunnableSender(new ServerAnswer(result, input.getAddress(), input.getPort())));

            } catch (IOException | ClassNotFoundException e) {
                executorService.submit(new RunnableSender(new ServerAnswer("", input.getAddress(), input.getPort())));

            }
        }
    }

    class RunnableSender implements Runnable {
        private final ServerAnswer input;

        public RunnableSender(ServerAnswer input) {
            this.input = input;
        }

        public void run() {
            ServerCommand answer = new ServerCommand("message", new String[]{""});
            System.out.println(input.getMessage());
            if(input.getMessage() instanceof String) {
                answer = new ServerCommand("message", new String[]{(String)input.getMessage()});
            }else if(input.getMessage() instanceof ArrayList){
                ArrayList<String> bandsString = new ArrayList<>();
                for(Object band:(ArrayList)input.getMessage()){
//                    System.out.println(band);
                    try {
                        bandsString.add(MusicBandsDataXMLSerializer.serializeMusicBand((MusicBand)band));
                    }
                    catch (JAXBException e){
                        System.out.println("Error while serializing");
                    }
                }
                String[] strs = new String[bandsString.size()];
                for (int i = 0; i < bandsString.size(); i++) {
                    strs[i] = bandsString.get(i);
                }
                answer = new ServerCommand("bands", strs);
            }else{
                System.out.println("Wrong type");

            }


            try {

                System.out.println("sending "+input.getMessage());
//                System.out.println(input.getPort());
                byte[] answerBuf = answer.serializeToString().getBytes();

                DatagramPacket packet = new DatagramPacket(answerBuf, answerBuf.length, input.getAddress(), input.getPort());

                serverSocket.send(packet);
            }catch (IOException e){
                System.out.println("ERROR WHILE SENDING");
            }

        }
    }

    class RunnableReceiver implements Runnable{
        private final ExecutorService executorRequests;
        private final ExecutorService executorSender;

        public RunnableReceiver(ExecutorService executorRequests, ExecutorService executorSender) {
            this.executorRequests = executorRequests;
            this.executorSender = executorSender;
        }

        public void run(){
            try{
                buf = new byte[65536];
                DatagramPacket packet
                        = new DatagramPacket(buf, buf.length);

                try {
                    serverSocket.receive(packet);
                } catch (SocketTimeoutException e) {
                    if (bufferedReader.ready()) {
                        processInput(bufferedReader.readLine());
                    }
                    return;
                }

                String received = new String(packet.getData(), 0, packet.getLength());
                //System.out.println("Received " + received);

                InetAddress address = packet.getAddress();
                int port = packet.getPort();

                String answer = "";

                RunnableHandler task = new RunnableHandler(new ServerAnswer(received, address, port), executorSender);
                executorRequests.submit(task);
            }catch (IOException e){
                System.out.println("Can't use server because of io exception");

            }
        }
    }

    void run() {


        ExecutorService executorRequests = Executors.newCachedThreadPool();
        ExecutorService executorSender = Executors.newFixedThreadPool(2);
        ExecutorService executorReceiver = Executors.newFixedThreadPool(2);
        boolean running = true;

            System.out.println("Running server...");
            System.out.print(">");

            while (running) {
                try {
                    Thread.currentThread().sleep(200);
                    executorReceiver.submit(new RunnableReceiver(executorSender, executorRequests));
                }catch (InterruptedException e){

                }
            }
            serverSocket.close();

    }


    public static void runServerSide() throws IOException {
        ServerSide serverSide = new ServerSide();
        serverSide.run();
    }
}
