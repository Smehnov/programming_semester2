import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.time.ZonedDateTime;

@XmlType(name = "MusicBand")
@XmlRootElement
public class MusicBand implements Comparable<MusicBand> {


    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    private String name; //Поле не может быть null, Строка не может быть пустой

    @XmlElement(name = "coordinates")
    private Coordinates coordinates; //Поле не может быть null
    @XmlElement(name = "creationDate")
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long numberOfParticipants; //Значение поля должно быть больше 0
    private MusicGenre genre; //Поле может быть null
    private Album bestAlbum; //Поле может быть null

    public ZonedDateTime getDate() {
        return this.creationDate;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setNumberOfParticipants(long numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public void setGenre(MusicGenre genre) {
        this.genre = genre;
    }

    public void setBestAlbum(Album bestAlbum) {
        this.bestAlbum = bestAlbum;
    }

    @XmlElement(name = "id")
    public long getId() {
        return id;
    }

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }
//
//    public Coordinates getCoordinates() {
//        return coordinates;
//    }
//
//    public ZonedDateTime getCreationDate() {
//        return creationDate;
//    }

    public long getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public MusicGenre getGenre() {
        return genre;
    }

    public Album getBestAlbum() {
        return bestAlbum;
    }

    @Override
    public String toString() {
        String info = "• id: " + this.id + '\n' +
                "• Name: " + this.name + '\n' +
                "• Coordinates: " + this.coordinates + '\n' +
                "• Creation Date: " + this.creationDate + '\n' +
                "• Number of Participants: " + this.numberOfParticipants + '\n' +
                "• Genre: " + this.genre + '\n' +
                "• Best Album: " + this.bestAlbum + '\n';
        return info;
    }

    @Override
    public int compareTo(MusicBand B2) {
        return this.getName().compareTo(B2.getName());
    }


}
