package me.mikethesupertramp.toolkit.database;

import java.sql.SQLException;

public interface SQLExceptionListener {
    void onSqlException(SQLException exception);
}
