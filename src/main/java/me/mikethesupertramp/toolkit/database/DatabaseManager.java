package me.mikethesupertramp.toolkit.database;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class DatabaseManager {
    private Connection connection;
    private SQLExceptionListener exceptionListener;
    private Set<AdvDao> daos = new HashSet<>();

    public DatabaseManager(SQLConnectionProvider connectionProvider, SQLExceptionListener exceptionListener) {
        System.out.println("Initializing database");
        this.exceptionListener = exceptionListener;
        connection = connectionProvider.getConnection();
        System.out.println("Database initialized successfully");
    }

    public void addDao(AdvDao dao) {
        initializeDao(dao);
        daos.add(dao);
    }

    private void initializeDao(AdvDao dao) {
        dao.initialize(connection, exceptionListener);
    }

    public <T> Dao<T> getFor(Class<T> type) {
        return daos.stream()
                .filter(dao -> dao.getDTOClass().equals(type))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Couldn't find DAO for " + type.getName()));
    }

    public void close() {
        if(connection!=null) {
            try {
                connection.close();
            } catch (SQLException e) {
                exceptionListener.onSqlException(e);
            }
        }
    }



}
