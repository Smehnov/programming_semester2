import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "album")
public class Album {
    @XmlElement(name = "name")
    private String name; //Поле не может быть null, Строка не может быть пустой
    @XmlElement(name = "length")
    private Long length; //Поле может быть null, Значение поля должно быть больше 0

    public Album(){
        this("Unnamed", (long)1);
    }
    public Album(String name, Long length) {
        this.name = name;
        this.length = length;
    }

    public String getName() {
        System.out.println(name);
        return name;
    }

    public Long getLength() {
        return length;
    }

}