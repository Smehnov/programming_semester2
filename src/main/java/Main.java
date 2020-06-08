import band_data.Inputting;
import band_data.MusicBandsData;
import client.ClientSide;
import commands.*;
import org.apache.commons.codec.digest.DigestUtils;
import server.ServerCommand;
import server.ServerSide;
import special.Constants;
import special.DataBase;
import special.UserInfo;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

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
        if(mode.equals("CREATESERVERDATABASENEW")){
            DataBase.createTable();
            System.exit(0);
        }
        if (mode.toLowerCase().equals("server")) {
            try {
                System.out.println("Enter server port");
                String portStr = sc.nextLine().trim();
                try {
                    int port = Integer.parseInt(portStr);
                    Constants.setPort(port);
                    System.out.println("running server on port " + port);
                    ServerSide.runServerSide();
                } catch (NumberFormatException e) {
                    System.out.println("Wrong port format, exiting...");
                    System.exit(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Can't start server, check all params");
                System.exit(0);
            }
        } else if (mode.toLowerCase().equals("client")) {
            System.out.println("Enter host name");
            String host = sc.nextLine().trim();
            System.out.println("Enter server port");
            String portStr = sc.nextLine().trim();
            try {
                int port = Integer.parseInt(portStr);
                Constants.setHost(host);
                Constants.setPort(port);
                System.out.println("Address of server set to " + host + ":" + port);

            } catch (NumberFormatException e) {
                System.out.println("Wrong port format, exiting...");
                System.exit(0);
            }

        } else {
            System.out.println("Not known mode, exit...");
            System.exit(0);
        }

        System.out.println("You need to be logged in to work with app");
        System.out.print("Enter >login or >register to \n>");
        String answer="";
        boolean answerIsOk = false;
        while (!answerIsOk) {
            answer = sc.nextLine().trim();
            if (answer.toLowerCase().equals("login") || answer.toLowerCase().equals("register")) {
                answerIsOk = true;
            } else {
                if (answer.toLowerCase().equals("exit")) {
                    System.out.println("Exiting...");
                    System.exit(0);
                } else {
                    System.out.print("Enter >login or >register\n>");
                }
            }
        }

        boolean usernameIsOk = false;
        String username="";
        while(!usernameIsOk){
            System.out.println("Enter username");
            System.out.print("Rules\n"+
                    "Length >=3\n" +
                    "Valid characters: a-z, A-Z, 0-9, points, dashes and underscores.\n>");
            username = sc.nextLine().trim();

            if (Pattern.matches("^[a-zA-Z0-9._-]{3,}$", username)){
                usernameIsOk = true;
            }
        }

        boolean passwordIsOk = false;
        String password="";
        while(!passwordIsOk){
            System.out.println("Enter password");
            System.out.print("Don't use spaces, length must be >=3\n>");
            password = sc.nextLine().trim();

            if (!password.contains(" ") && password.length()>=3){
                passwordIsOk = true;
            }
        }

        Constants.setUserLogin(username);
        Constants.setUserPassword(password);

        if (answer.toLowerCase().equals("login")){
            try {
                ServerCommand serverCommand = new ServerCommand("login", new String[0]);
                serverCommand.setUserLogin(Constants.getUserLogin());
                serverCommand.setUserPassword(Constants.getUserPassword());
                String message = serverCommand.serializeToString();

                String received = ClientSide.sendMessage(message);

                System.out.println(received);
                if (received.equals("Password invalid") || received.equals("This user doesn't exist")||received.equals("Can't connect to server")){
                    System.out.println("Exit...");
                    System.exit(0);
                }
            } catch (IOException|java.nio.channels.UnresolvedAddressException e) {
                System.out.println("Error while sending message to server...");
            }
        }
        if (answer.toLowerCase().equals("register")){
            try {
                ServerCommand serverCommand = new ServerCommand("register", new String[0]);
                serverCommand.setUserLogin(Constants.getUserLogin());
                serverCommand.setUserPassword(Constants.getUserPassword());
                String message = serverCommand.serializeToString();

                String received = ClientSide.sendMessage(message);

                System.out.println(received);
                if (received.equals("This username already used")||received.equals("Can't connect to server")){
                    System.out.println("Exit...");
                    System.exit(0);
                }
            } catch (IOException e) {
                System.out.println("Error while sending message to server...");
            }
        }

        //TODO send register/login command with data to check


        MusicBandsData musicBandsData = new MusicBandsData();

        HelpCommand helpCommand = new HelpCommand();
        InfoCommand infoCommand = new InfoCommand();
        ExitCommand exitCommand = new ExitCommand();
        ShowCommand showCommand = new ShowCommand();
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
