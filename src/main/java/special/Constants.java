package special;

public class Constants {
    static String host = "localhost";
    static int port = 7000;

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        Constants.host = host;
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        Constants.port = port;
    }
}
