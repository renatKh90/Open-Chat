package server;

import java.sql.*;

public class SqlHandler {
    private static Connection connection;
    private static PreparedStatement getNickname;
    private static PreparedStatement registration;
    private static PreparedStatement changeNick;

    private static void prepareStatements() throws SQLException {
        getNickname = connection.prepareStatement("SELECT nickname FROM users WHERE login = ? AND password = ?;");
        registration = connection.prepareStatement("INSERT INTO users(login, password, nickname) VALUES (? ,? ,? );");
        changeNick = connection.prepareStatement("UPDATE users SET nickname = ? WHERE nickname = ?;");
    }

    public static String getNicknameByLoginAndPassword(String login, String password) {
        String nick = null;
        try {
            getNickname.setString(1, login);
            getNickname.setString(2, password);
            ResultSet rs = getNickname.executeQuery();
            if (rs.next()) {
                nick = rs.getString(1);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nick;
    }

    public static boolean registration(String login, String password, String nickname) {
        try {
            registration.setString(1, login);
            registration.setString(2, password);
            registration.setString(3, nickname);
            registration.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean changeNick(String oldNickname, String newNickname) {
        try {
            changeNick.setString(1, newNickname);
            changeNick.setString(2, oldNickname);
            changeNick.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean connectToDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:main.db");
            prepareStatements();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void disconnectFromDB() {
        try {
            registration.close();
            getNickname.close();
            changeNick.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
