import java.io.IOException;

/**
 * Class for command 'executing script'
 * @implements Command
 */
public class ExecuteScriptCommand implements Command {
    public ExecuteScriptCommand() {
        CommandExecutor.addCommand("execute_script", this);
    }

    @Override
    public void execute(String arg, MusicBandsData data) {


        if (arg != null) {
            try {
                if (Inputting.runningScriptNames.contains(arg)) {
                    System.out.println("This script has been already called. Avoid endless recursion.");
                    return;
                }
                Inputting.parseScript(arg);
                System.out.println("Reading commands form file " + arg);
            } catch (IOException e) {
                System.out.println("Error while reading script file " + arg);
            }
        } else {
            System.out.println("Wrong command format. Check >help for more info");
        }
    }
}
