package special;

public class Constants {
    static String host = "localhost";
    static int port = 1000;
    static String userLogin = "alex";
    static String userPassword ="123";
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

    public static String getUserLogin() {
        return userLogin;
    }

    public static void setUserLogin(String userLogin) {
        Constants.userLogin = userLogin;
    }

    public static String getUserPassword() {
        return userPassword;
    }

    public static void setUserPassword(String userPassword) {
        Constants.userPassword = userPassword;
    }
}
