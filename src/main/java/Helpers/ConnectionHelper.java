package Helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {
    private static String uri = "jdbc:mysql://localhost:3306/entregable?createDatabaseIfNotExist=true";
    private static String root = "root";
    private static String password = "password";
    private static Connection connection;

    private ConnectionHelper() {
    }

    public static Connection startConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(uri, root, password);
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }

    public Connection getConnection(){
        return connection;
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }
}
