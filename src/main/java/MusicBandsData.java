import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.PriorityQueue;


@XmlType(name = "musicbanddata")
@XmlRootElement
/**
 * Class-wrapper for collection of music bands
 */
public class MusicBandsData {

    /**
     * Field for inizialization time
     */
    @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
    private LocalDateTime inizializationTime;

    /**
     * Field for main collection
     */
    private PriorityQueue<MusicBand> queue;

    /**
     * Generator for empty MusicBandsData
     */
    public MusicBandsData() {
        queue = new PriorityQueue<>();
        inizializationTime = LocalDateTime.now();
    }

    /**
     * Setter for time of Inizialization (straight cheating)
     *
     * @param inizializationTime
     */

    public void setInitializationTime(LocalDateTime inizializationTime) {
        this.inizializationTime = inizializationTime;
    }


    /**
     * Setter for PriorityQueue - main collection of MusicBandsData
     *
     * @param queue - PriorityQueue
     */
    public void setQueue(PriorityQueue<MusicBand> queue) {
        this.queue = queue;
    }

    @XmlElement(name = "band", nillable = true)
    /**
     * Getter for PriorityQueue - main collection of MusicBandsData
     * @return this.queue
     */
    public PriorityQueue<MusicBand> getQueue() {
        return queue;
    }


    /**
     * Getter for time of inizialization
     *
     * @return this.inizializationTime
     */
    public LocalDateTime getInizializationTime() {
        return this.inizializationTime;
    }

    public long getGreatestId() {
        long greatestId = 0;
        for (MusicBand band : queue) {
            if (band.getId() > greatestId) {
                greatestId = band.getId();
            }

        }
        return greatestId;
    }

    public void addMusicBand(MusicBand musicBand) {
        musicBand.setId(this.getGreatestId() + 1);
        this.queue.add(musicBand);
    }

    public MusicBand getElementById(long id) {
        for (MusicBand band : queue) {
            if (band.getId() == id) {
                return band;
            }
        }
        return null;
    }

    public ArrayList<Long> getListOfIds(){
        ArrayList<Long> ids = new ArrayList<>();
        for (MusicBand band:
             queue) {
            ids.add(band.getId());
        }
        return ids;
    }

    public void updateMusicBand(long id, MusicBand musicBand) {
        MusicBand updatingBand = new MusicBand();
        for (MusicBand band :
                queue) {
            if (band.getId() == id) {
                updatingBand = band;
            }
        }
        queue.remove(updatingBand);
        musicBand.setId(id);
        queue.add(musicBand);
    }
}


