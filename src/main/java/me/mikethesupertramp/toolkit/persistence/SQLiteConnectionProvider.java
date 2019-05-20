package me.mikethesupertramp.toolkit.persistence;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnectionProvider implements SQLConnectionProvider {
    private final String url;
    private final String path;
    private Connection connection;

    public SQLiteConnectionProvider(String path) {
        this.path = path;
        this.url = "jdbc:sqlite:" + path;
    }

    public Connection getConnection() {
        if(connection == null) {
            initializeConnection();
        }
        return connection;
    }

    private void initializeConnection() {
        File db = new File(path);
        db.getParentFile().mkdirs();

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("sqlite driver could not be loaded", e);
        } catch (SQLException e) {
            throw new RuntimeException("couldnt connect to database", e);
        }
    }
}
