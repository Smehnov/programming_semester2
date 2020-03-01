import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "coordinates")
/**class for (x,y) Coordinates*/
public class Coordinates {
    @XmlElement(name = "x")
    private Integer x; //Поле не может быть null
    @XmlElement(name = "y")
    private Long y; //Поле не может быть null

/**Generator for default (0,0) coords.
 */
    public Coordinates() {
        this(0, (long)0);
    }
    /**Generator
     * @param x
     * @param y
     */
    public Coordinates(Integer x, Long y) {
        this.x = x;
        this.y = y;
    }

    /** Getter for x
     *
     * @return this.x
     */
    public Integer getX() {
        return this.x;
    }

    /**Getter for y
     *
     * @return this.y
     */
    public Long getY() {
        return this.y;
    }

    @Override
    public String toString(){
        return "{x= "+ this.x+" y= " + this.y+"}";
    }
}