package server;

import java.io.*;
import java.util.Arrays;

public class ServerCommand implements Serializable {
    private String type;
    private String[] params;
    public ServerCommand(String type, String[] params){
        this.type = type;
        this.params = params;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }
    public String serializeToString() throws IOException{

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(this);
        String result = outputStream.toString();
        return result;
    }
    @Override
    public String toString() {
        return "ServerCommand{" +
                "type='" + type + '\'' +
                ", params=" + Arrays.toString(params) +
                '}';
    }
}
