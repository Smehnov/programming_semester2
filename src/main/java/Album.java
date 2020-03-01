import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Class Album with Name and length as fields
 */
@XmlType(name = "album")
public class Album {
    @XmlElement(name = "name")
    private String name; //Поле не может быть null, Строка не может быть пустой
    @XmlElement(name = "length")
    private Long length; //Поле может быть null, Значение поля должно быть больше 0

    /**Constructor for unnamed album*/
    public Album(){
        this("Unnamed", (long)1);
    }

    /**Constructor
     * @param name - name of album
     * @param length - number of songs in album
     */
    public Album(String name, Long length) {
        this.name = name;
        this.length = length;
    }

    /**Getter for name
     * @return this.name
     */
    public String getName() {
        System.out.println(name);
        return name;
    }

    /**
     * Getter for length
     * @return this.length
     */
    public Long getLength() {
        return length;
    }

    @Override
    public String toString(){
        return this.name;
    }

}