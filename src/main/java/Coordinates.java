import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "coordinates")
/**class for (x,y) Coordinates*/
public class Coordinates {
    @XmlElement(name = "x")
    private Double x; //Поле не может быть null
    @XmlElement(name = "y")
    private float y; //Поле не может быть null

/**Generator for default (0,0) coords.
 */
    public Coordinates() {
        this(0d, 0f);
    }
    /**Generator
     * @param x
     * @param y
     */
    public Coordinates(Double x, float y) {
        this.x = x;
        this.y = y;
    }


    /** Getter for x
     *
     * @return this.x
     */
    public Double getX() {
        return this.x;
    }

    /**Getter for y
     *
     * @return this.y
     */
    public Float getY() {
        return this.y;
    }

    @Override
    public String toString(){
        return "{x= "+ this.x+" y= " + this.y+"}";
    }
}