import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Inputting file
 */
public class Inputting {
    private static boolean fromFile = false;
    private static LinkedList<String> scriptCommands = new LinkedList<>();

    public static boolean isFromFile() {
        return fromFile;
    }

    //TODO CHECKING
    public static void parseScript(String scriptPath) throws IOException {
        String data = "";
        File file = new File(scriptPath);

        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        while (bis.available() > 0) {
            data += (char) bis.read();
        }
        String lastLine = "";
        for (char c : data.toCharArray()) {
            if (c == '\n') {
                scriptCommands.add(lastLine);
                lastLine = "";

            } else {
                lastLine += c;
            }
        }
        fromFile = true;
    }


    public static String readLine() {
        if (fromFile) {
            String command = scriptCommands.poll();
            if (scriptCommands.isEmpty()) {
                fromFile = false;
            }

            System.out.print(">");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Problem with sleepping");
                ;
            }

            System.out.println(command);
            return command;

        } else {

            Scanner sc = new Scanner(System.in);
            System.out.print(">");
            return sc.nextLine();
        }
    }
}
