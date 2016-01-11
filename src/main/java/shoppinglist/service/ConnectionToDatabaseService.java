package shoppinglist.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Romcikas on 12/3/2015.
 */
public class ConnectionToDatabaseService {
    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_CONNECTION = "jdbc:mysql://localhost/shoppinglist";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public static Connection connectToMysql() {
        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException();
        }
        try {
            dbConnection = DriverManager
                    .getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
           throw new RuntimeException();
        }
    }
}
