package special;
import java.sql.*;

public class BdTest {

    public static final String DB_URL = "jdbc:postgresql://localhost:5432/studs";
    public static final String login = "USERNAME";
    public static final String password = "PASSWORD";

    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, login, password);
            connection.setAutoCommit(false);
            System.out.println("Соединение с СУБД выполнено.");
            Statement stmt = connection.createStatement();
            String sql =
                    "CREATE SEQUENCE users_Derechi_Smehnov_sequence INCREMENT BY 1;"+
                            "CREATE SEQUENCE Music_Bands_Derechi_Smehnov_sequence INCREMENT BY 1;"+
                            "CREATE TABLE Derechi_Smehnov_USERS (ID int PRIMARY KEY NOT NULL, LOGIN TEXT NOT NULL, PASSWORD text);" +
                            "CREATE TABLE Derechi_Smehnov_Music_Bands " +
                            "(ID int PRIMARY KEY     NOT NULL," +
                            " NAME           TEXT    NOT NULL, "+
                            " COORDINATE_x    FLOAT     NOT NULL, " +
                            " COORDINATE_y    FLOAT     NOT NULL, " +
                            " CREATION_DATE   TIMESTAMP , "+
                            " NUMBER_OF_PARTICIPANTS INTEGER NOT NULL,"+
                            " GENRE TEXT NOT NULL," +
                            " BEST_ALBOM TEXT," +
                            " USER_ID int NOT NULL)";

            stmt.executeUpdate(sql);
            stmt.close();
            connection.commit();
            connection.close();
            System.out.println("Отключение от СУБД выполнено.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("JDBC драйвер для СУБД не найден!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL!");
        }
    }
}
