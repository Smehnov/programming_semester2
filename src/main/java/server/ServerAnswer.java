package server;

import java.net.InetAddress;

public class ServerAnswer {
    private Object message;
    private InetAddress address;
    private int port;

    public ServerAnswer(Object message, InetAddress address, int port) {
        this.message = message;
        this.address = address;
        this.port = port;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
