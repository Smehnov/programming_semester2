import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.ZonedDateTime;

/**
 * Music band class
 */
@XmlType(name = "MusicBand")
@XmlRootElement
public class MusicBand implements Comparable<MusicBand> {

    public MusicBand() {
        this.id = 0;
        this.creationDate = ZonedDateTime.now();
    }

    /**
     * Field for unique ID of music band
     */
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    /**
     * Field for name of band
     */
    private String name; //Поле не может быть null, Строка не может быть пустой

    /**
     * Field for coordinates
     */
    @XmlElement(name = "coordinates")
    private Coordinates coordinates; //Поле не может быть null
    /**
     * Field for auto-generated value of creation date
     */
    @XmlJavaTypeAdapter(value = ZonedDateTimeAdapter.class)
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    /**
     * Field for number of Participants of Music Band
     */
    private Integer numberOfParticipants; //Значение поля должно быть больше 0
    /**
     * Field for Music Genre of group. Could be null
     */
    private MusicGenre genre; //Поле может быть null
    /**
     * Filed for best album of Band. Could be null
     */
    private Album bestAlbum; //Поле может быть null

    /**
     * Getter for creation date
     *
     * @return this.creationDate
     */
    public ZonedDateTime getDate() {
        return this.creationDate;
    }

    /**
     * Setter for ID
     *
     * @param id - unique ID of music band.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Setter for name
     *
     * @param name - name of music band
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter for coordinates
     *
     * @param coordinates
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCoordinates(Double coordX, Float coordY) {
        this.coordinates = new Coordinates(coordX, coordY);
    }

    /**
     * Setter for creation date
     *
     * @param creationDate
     */
    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Setter for number of participants
     *
     * @param numberOfParticipants - number of music band
     */
    public void setNumberOfParticipants(Integer numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    /**
     * Setter for genre of music bands
     *
     * @param genre - genre of music band
     */
    public void setGenre(MusicGenre genre) {
        this.genre = genre;
    }

    /**
     * Setter for the best album
     *
     * @param bestAlbum - best album
     */
    public void setBestAlbum(Album bestAlbum) {
        this.bestAlbum = bestAlbum;
    }

    /**
     * Getter for unique ID of music band
     *
     * @return id
     */
    @XmlElement(name = "id")
    public long getId() {
        return id;
    }

    /**
     * Getter for name of music band
     *
     * @return name
     */
    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    /**
     * Getter for number of participants for music bands
     *
     * @return numberOfParticipants
     */
    public Integer getNumberOfParticipants() {
        return numberOfParticipants;
    }

    /**
     * Getter for genre of music bands
     *
     * @return genre
     */
    public MusicGenre getGenre() {
        return genre;
    }

    /**
     * Getter for the best album of music bands
     *
     * @return bestAlbum
     */
    public Album getBestAlbum() {
        return bestAlbum;
    }

    /**
     * to string transformation into list of params and their values
     */
    @Override
    public String toString() {
        String info = "Music band: \n" +
                "   • id: " + this.id + '\n' +
                "   • Name: " + this.name + '\n' +
                "   • Coordinates: " + this.coordinates + '\n' +
                "   • Creation Date: " + this.creationDate + '\n' +
                "   • Number of Participants: " + this.numberOfParticipants + '\n' +
                "   • Genre: " + this.genre + '\n' +
                "   • Best Album: " + this.bestAlbum + '\n';
        return info;
    }

    /**
     * Method to compare music bands via their names
     */
    @Override
    public int compareTo(MusicBand B2) {
        return this.getName().compareTo(B2.getName());
    }


}
