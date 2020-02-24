import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.time.LocalDateTime;
import java.util.PriorityQueue;

@XmlType(name = "musicbanddata")
@XmlRootElement
public class MusicBandsData {
    @XmlElement(name = "inizializationTime")
    private LocalDateTime inizializationTime;

    private PriorityQueue<MusicBand> queue;

    public MusicBandsData() {
        queue = new PriorityQueue<>();
        inizializationTime = LocalDateTime.now();
    }

    public void setInitializationTime(LocalDateTime time) {
        this.inizializationTime = time;
    }

    public void setQueue(PriorityQueue<MusicBand> queue) {
        this.queue = queue;
    }

    @XmlElement(name = "band", nillable = true)
    public PriorityQueue<MusicBand> getQueue() {
        return queue;
    }

    public LocalDateTime getInizializationTime() {
        return this.inizializationTime;
    }
}
