import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.time.LocalDateTime;
import java.util.PriorityQueue;



@XmlType(name = "musicbanddata")
@XmlRootElement
/**
 * Class-wrapper for collection of music bands
 */
public class MusicBandsData {
    @XmlElement(name = "inizializationTime")
    /** Field for inizialization time*/
    private LocalDateTime inizializationTime;

    /**Field for main collection*/
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
     * @param time
     */
    public void setInitializationTime(LocalDateTime time) {
        this.inizializationTime = time;
    }

    /**
     * Setter for PriorityQueue - main collection of MusicBandsData
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
     * @return this.inizializationTime
     */
    public LocalDateTime getInizializationTime() {
        return this.inizializationTime;
    }
}
