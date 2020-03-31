package band_data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * class for (x,y) band_data.Coordinates
 */
@XmlType(name = "coordinates")
public class Coordinates {
    @XmlElement(name = "x")
    private Double x; //Поле не может быть null
    @XmlElement(name = "y")
    private float y; //Поле не может быть null

    public Coordinates() {
        this(0d, 0f);
    }

    public Coordinates(Double x, float y) {
        this.x = x;
        this.y = y;
    }

    public Double getX() {
        return this.x;
    }

    public Float getY() {
        return this.y;
    }

    @Override
    public String toString() {
        return "{x= " + this.x + " y= " + this.y + "}";
    }
}