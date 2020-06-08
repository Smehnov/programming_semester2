package server;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.io.*;
import java.util.Arrays;

public class ServerCommand implements Serializable {
    private String type;
    private String userLogin;
    private String userPassword;

    private String[] params;

    public ServerCommand(String type, String[] params) {
        this.type = type;
        this.params = params;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
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

    public String serializeToString() throws IOException {


        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream so = new ObjectOutputStream(bo);
        so.writeObject(this);
        so.flush();
        return Base64.encode(bo.toByteArray());
    }

    public static ServerCommand deserializeFromString(String serialized) throws IOException, ClassNotFoundException {
        byte b[] = Base64.decode(serialized);
        ByteArrayInputStream bi = new ByteArrayInputStream(b);
        ObjectInputStream si = new ObjectInputStream(bi);
        return (ServerCommand) si.readObject();
    }


    @Override
    public String toString() {
        return "ServerCommand{" +
                "type='" + type + '\'' +
                ", userLogin='" + userLogin + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", params=" + Arrays.toString(params) +
                '}';
    }
}
