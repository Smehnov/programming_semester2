
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.ZonedDateTime;
/**
 * Class for serializing xml data fields
 */

public class ZonedDateTimeAdapter extends XmlAdapter<String, ZonedDateTime> {
    public ZonedDateTime unmarshal(String v) throws Exception {
        return ZonedDateTime.parse(v);
    }

    public String marshal(ZonedDateTime v) throws Exception {
        return v.toString();
    }
}