public class Album {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Long length; //Поле может быть null, Значение поля должно быть больше 0

    public Album(String name, Long length){
        this.name = name;
        this.length = length;
    }
}