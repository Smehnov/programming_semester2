import java.time.ZonedDateTime;

public class MusicBand {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long numberOfParticipants; //Значение поля должно быть больше 0
    private MusicGenre genre; //Поле может быть null
    private Album bestAlbum; //Поле может быть null

    public ZonedDateTime getDate(){
        return this.creationDate;
    }

    public void setId(long id){
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates){
        this.coordinates = coordinates;
    }

    public void setCreationDate(ZonedDateTime creationDate){
        this.creationDate = creationDate;
    }

    public void setNumberOfParticipants(long numberOfParticipants){
        this.numberOfParticipants = numberOfParticipants;
    }

    public void setGenre(MusicGenre genre){
        this.genre = genre;
    }

    public void setBestAlbum(Album bestAlbum){
        this.bestAlbum = bestAlbum;
    }

    @Override
    public String toString() {
        String info = "• id: " + this.id +'\n'+
                "• Name: " + this.name +'\n'+
                "• Coordinates: " + this.coordinates+'\n'+
                "• Creation Date: " + this.creationDate + '\n'+
                "• Number of Participants: " + this.numberOfParticipants + '\n'+
                "• Genre: " + this.genre+'\n'+
                "• Best Album: " + this.bestAlbum+'\n';
        return info;
    }
}


