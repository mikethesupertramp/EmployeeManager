package me.mikethesupertramp.toolkit.database;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AdvDao<T> implements Dao<T> {
    private SQLExceptionListener exceptionListener;
    private boolean initialized;

    void initialize(Connection connection, SQLExceptionListener exceptionListener) {
        this.exceptionListener = exceptionListener;
        createTableIfNotExists(connection);
        prepareStatements(connection);
        this.initialized = true;
    }

    protected  void onSQLException(SQLException e) {
        exceptionListener.onSqlException(e);
    }

    protected boolean isInitialized() {
        return initialized;
    }
    protected abstract void prepareStatements(Connection connection);
    protected abstract void createTableIfNotExists(Connection connection);
    protected abstract Class<T> getDTOClass();
}
