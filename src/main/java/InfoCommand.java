public class InfoCommand implements Command {
    public InfoCommand(){
        CommandExecutor.addCommand("info", this);
    }

    public void execute(String arg, MusicBandsData data){
        System.out.println("Тип: PriorityQueue\n" +
                "Дата инициализации: " + data.getInizializationTime() + '\n' +
                "Количество элементов: " + data.getQueue().size() + '\n'
        );
    }
}
