import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "coordinates")

public class Coordinates {
    @XmlElement(name = "x")
    private Integer x; //Поле не может быть null
    @XmlElement(name = "y")
    private Long y; //Поле не может быть null

    public Coordinates() {
        this(0, (long)0);
    }

    public Coordinates(Integer x, Long y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return this.x;
    }

    public Long getY() {
        return this.y;
    }
}