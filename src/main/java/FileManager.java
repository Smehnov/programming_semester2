import java.io.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * Class reading/writing xml files
 */
public class FileManager {
    /**
     * Method that save your collection in xml file with path ...
     * @param collection - collection to save
     * @param filePath - path to file
     * @throws JAXBException
     */
    public static void saveToXml(MusicBandsData collection, String filePath) throws JAXBException {
        //StringWriter writer = new StringWriter();

        File file = new File(filePath);
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            JAXBContext context = JAXBContext.newInstance(MusicBandsData.class, MusicBand.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(collection, writer);


            //String result = writer.toString();
            //System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("file writing error");
        }
    }

    /**
     * Method for reading and spreading data from xml file
     * @param filePath - path for xml file
     * @return MusicBandData
     * @throws JAXBException
     */
    public static MusicBandsData readFromXML(String filePath) throws JAXBException {
        //TODO change to fileread after fixing date problem
        String xmldata = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<musicBandsData>\n" +
                "    <band>\n" +
                "        <coordinates>\n" +
                "            <x>100</x>\n" +
                "            <y>20</y>\n" +
                "        </coordinates>\n" +
                "        <bestAlbum>\n" +
                "            <name>Cracken</name>\n" +
                "            <length>22</length>\n" +
                "        </bestAlbum>\n" +
                "        <genre>BLUES</genre>\n" +
                "        <id>1</id>\n" +
                "        <name>alestorm</name>\n" +
                "        <numberOfParticipants>9</numberOfParticipants>\n" +
                "    </band>\n" +
                "    <band>\n" +
                "        <coordinates>\n" +
                "            <x>100</x>\n" +
                "            <y>20</y>\n" +
                "        </coordinates>\n" +
                "        <bestAlbum>\n" +
                "            <name>Cracken</name>\n" +
                "            <length>22</length>\n" +
                "        </bestAlbum>\n" +
                "        <genre>BLUES</genre>\n" +
                "        <id>1</id>\n" +
                "        <name>alestorm</name>\n" +
                "        <numberOfParticipants>9</numberOfParticipants>\n" +
                "    </band>\n" +
                "</musicBandsData>\n";

        StringReader reader = new StringReader(xmldata);

        JAXBContext context = JAXBContext.newInstance(MusicBandsData.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        MusicBandsData data = (MusicBandsData) unmarshaller.unmarshal(reader);
        return data;
    }
}
