package special;

import band_data.*;

import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public class DataBase {

    public static final String DB_URL = "jdbc:postgresql://localhost:5432/studs";
    public static final String login = SPEC.username_db;
    public static final String password = SPEC.password_db;


    //TODO test db
    public static UserInfo getUser(String username) {
        UserInfo userInfo = null;
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, login, password);
            connection.setAutoCommit(false);
            String insertSQL = "SELECT * FROM Derechi_Smehnov_USERS WHERE login LIKE ?";
            PreparedStatement stat = connection.prepareStatement(insertSQL);
            stat.setString(1, username);
            ResultSet res = stat.executeQuery();

            while (res.next()) {
                int user_id = res.getInt(1);
                String user_login = res.getString(2);
                String user_password = res.getString(3);
                userInfo = new UserInfo(user_id, user_login, user_password);
            }


        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
            System.out.println("JDBC driver not found");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL error!");
        }
        return userInfo;
    }

    public static void createUser(String username, String upassword) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, login, password);
            connection.setAutoCommit(false);
            String insertSQL = "INSERT INTO Derechi_Smehnov_USERS (ID,LOGIN, PASSWORD) VALUES (?, ?, ?)";
            PreparedStatement stat = connection.prepareStatement(insertSQL);
            stat.setInt(1, username.hashCode());

            stat.setString(2, username);
            stat.setString(3, upassword);
            stat.executeUpdate();
            stat.close();
            connection.commit();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("JDBC driver not found");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL error!");
        }
    }

    public static void saveMusicBandsDataForUserWithId(int user_id, MusicBandsData musicBandsData) {
        MusicBandsData oldData = DataBase.getMusicBandsDataByUserId(user_id);
        List<Long> oldbandsIds = oldData.getListOfIds();
        MusicBand[] bands = musicBandsData.getAllBands();
        for (MusicBand band :
                bands) {
            if (oldbandsIds.contains(band.getId())) {

                try {
                    Class.forName("org.postgresql.Driver");
                    Connection connection = DriverManager.getConnection(DB_URL, login, password);
                    connection.setAutoCommit(false);
                    String insertSQL = "UPDATE Derechi_Smehnov_Music_Bands" +
                            "NAME = ?" +
                            "COORDINATE_x = ?" +
                            "COORDINATE_y = ?" +
                            "CREATION_DATE = ?" +
                            "NUMBER_OF_PARTICIPANTS = ?" +
                            "GENRE = ?" +
                            "BEST_ALBUM_NAME = ?" +
                            "BEST_ALBUM_LENGTH = ?" +
                            " WHERE ID=?;";
                    PreparedStatement stat = connection.prepareStatement(insertSQL);
                    stat.setString(1, band.getName());
                    stat.setDouble(2, band.getCoordinates().getX());
                    stat.setFloat(3, band.getCoordinates().getY());
                    stat.setTimestamp(4, Timestamp.valueOf(band.getCreationDate().toLocalDateTime()));
                    stat.setInt(5, band.getNumberOfParticipants());
                    stat.setString(6, band.getGenre().toString());
                    Album bestAlbum = band.getBestAlbum();
                    if (bestAlbum != null) {
                        stat.setString(7, bestAlbum.getName());
                        stat.setLong(8, bestAlbum.getLength());
                    } else {
                        stat.setString(7, "");
                        stat.setLong(8, 0);
                    }
                    stat.setInt(9, user_id);


                    stat.executeUpdate();
                    stat.close();
                    connection.commit();
                    connection.close();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    System.out.println("JDBC driver not found");
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("SQL error!");
                }


            } else {

                try {
                    Class.forName("org.postgresql.Driver");
                    Connection connection = DriverManager.getConnection(DB_URL, login, password);
                    connection.setAutoCommit(false);
                    String insertSQL = "INSERT INTO Derechi_Smehnov_Music_Bands(ID, NAME, COORDINATE_x, COORDINATE_y, CREATION_DATE, NUMBER_OF_PARTICIPANTS, GENRE, BEST_ALBUM_NAME, BEST_ALBUM_LENGTH, USER_ID) " +
                            "VALUES(nextval('Music_Bands_Derechi_Smehnov_sequence'), ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement stat = connection.prepareStatement(insertSQL);
                    stat.setString(1, band.getName());
                    stat.setDouble(2, band.getCoordinates().getX());
                    stat.setFloat(3, band.getCoordinates().getY());
                    stat.setTimestamp(4, Timestamp.valueOf(band.getCreationDate().toLocalDateTime()));
                    stat.setInt(5, band.getNumberOfParticipants());
                    stat.setString(6, band.getGenre().toString());
                    Album bestAlbum = band.getBestAlbum();
                    if (bestAlbum != null) {
                        stat.setString(7, bestAlbum.getName());
                        stat.setLong(8, bestAlbum.getLength());
                    } else {
                        stat.setString(7, "");
                        stat.setLong(8, 0);
                    }
                    stat.setInt(9, user_id);


                    stat.executeUpdate();
                    stat.close();
                    connection.commit();
                    connection.close();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    System.out.println("JDBC driver not found");
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("SQL error!");
                }
            }
        }

    }

    public static MusicBandsData getMusicBandsDataByUserId(int user_id) {
        //TODO
        MusicBandsData musicBandsData = new MusicBandsData();
        System.out.println(user_id);
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, login, password);
            connection.setAutoCommit(false);
            String insertSQL = "SELECT * FROM Derechi_Smehnov_Music_Bands WHERE USER_ID=?";
            PreparedStatement stat = connection.prepareStatement(insertSQL);
            stat.setInt(1, user_id);
            ResultSet res = stat.executeQuery();

            while (res.next()) {

                MusicBand musicBand = new MusicBand();
                long musicBandId = res.getLong("ID");
                String musicBandName = res.getString("NAME");
                double coordinateX = res.getDouble("COORDINATE_x");
                float coordinateY = res.getFloat("COORDINATE_y");
                ZonedDateTime creationDate = res.getTimestamp("CREATION_DATE").toLocalDateTime().atZone(ZoneId.of("Europe/Moscow"));
                int numberOfParticipants = res.getInt("NUMBER_OF_PARTICIPANTS");
                MusicGenre genre = MusicGenre.valueOf(res.getString("GENRE"));
                String bestAlbumName = res.getString("BEST_ALBUM_NAME");
                Long bestAlbumLength = res.getLong("BEST_ALBUM_LENGTH");

                Album bestAlbum = new Album(bestAlbumName, bestAlbumLength);
                Coordinates coordinates = new Coordinates(coordinateX, coordinateY);
                musicBand.setCoordinates(coordinates);
                musicBand.setBestAlbum(bestAlbum);
                musicBand.setName(musicBandName);
                musicBand.setId(musicBandId);
                musicBand.setGenre(genre);
                musicBand.setNumberOfParticipants(numberOfParticipants);
                musicBand.setCreationDate(creationDate);


                musicBandsData.addMusicBand(musicBand);
            }

            return musicBandsData;


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("JDBC driver not found");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL error!");
        }
        return musicBandsData;
    }


    public static void createTable() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, login, password);
            connection.setAutoCommit(false);
            Statement stmt = connection.createStatement();
            String sql =
                    "CREATE SEQUENCE users_Derechi_Smehnov_sequence INCREMENT BY 1;" +
                            "CREATE SEQUENCE Music_Bands_Derechi_Smehnov_sequence INCREMENT BY 1;" +
                            "CREATE TABLE Derechi_Smehnov_USERS (ID int PRIMARY KEY NOT NULL, LOGIN TEXT NOT NULL, PASSWORD text);" +
                            "CREATE TABLE Derechi_Smehnov_Music_Bands " +
                            "(ID int PRIMARY KEY     NOT NULL," +
                            " NAME           TEXT    NOT NULL, " +
                            " COORDINATE_x    INTEGER     NOT NULL, " +
                            " COORDINATE_y    FLOAT    NOT NULL, " +
                            " CREATION_DATE   TIMESTAMP , " +
                            " NUMBER_OF_PARTICIPANTS INTEGER NOT NULL," +
                            " GENRE TEXT NOT NULL," +
                            " BEST_ALBUM_NAME TEXT," +
                            " BEST_ALBUM_LENGTH INTEGER," +
                            " USER_ID int NOT NULL)";

            stmt.executeUpdate(sql);
            stmt.close();
            connection.commit();
            connection.close();
            System.out.println("DATABASE disconnected.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("JDBC driver not found");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL error!");
        }
    }
}
