package me.mikethesupertramp.toolkit.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AdvDao<T> implements Dao<T> {
    private SQLExceptionListener exceptionListener;
    private List<DataChangeListener<T>> listeners = new ArrayList<>();
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


    public void addListener(DataChangeListener<T> listener) {
        listeners.add(listener);
    }

    public void removeListener(DataChangeListener<T> listener) {
        listeners.remove(listener);
    }

    protected void notifyListeners(DataChangeEvent<T> e) {
        listeners.forEach(listener -> listener.onDataChanged(e));
    }
}
