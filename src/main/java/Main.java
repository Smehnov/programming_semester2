import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean isWorking = true;

        while (isWorking) {
            System.out.println("\nChoose your action:");
            String action = sc.next();

            //TODO regex for custom values( id / {element})
            switch (action) {
                case "help":
                    //TODO help
                    showHelp();
                    break;
                case "info":
                    //TODO info
                    break;
                case "show":
                    //TODO show
                    break;
                case "add {element}":
                    //TODO add {element}
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
                    //TODO save
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
                    System.out.println("Command is wrong");
                    break;
            }

        }
    }


    static void showHelp() {
        System.out.println("help : вывести справку по доступным командам\n" +
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add {element} : добавить новый элемент в коллекцию\n" +
                "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_by_id id : удалить элемент из коллекции по его id\n" +
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
    }
}
