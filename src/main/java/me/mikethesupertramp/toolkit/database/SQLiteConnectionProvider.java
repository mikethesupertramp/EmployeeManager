package me.mikethesupertramp.toolkit.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnectionProvider implements SQLConnectionProvider {
    private final String url;
    private Connection connection;

    public SQLiteConnectionProvider(String path) {
        url = "jdbc:sqlite:"+path;
    }

    public Connection getConnection() {
        if(connection == null) {
            initializeConnection();
        }

        return connection;
    }

    private void initializeConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("sqlite driver could not be loaded");
        } catch (SQLException e) {
            throw new RuntimeException("couldnt connect to database");
        }
    }
}
