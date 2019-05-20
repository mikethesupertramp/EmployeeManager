package me.mikethesupertramp.toolkit.persistence;

import java.sql.SQLException;

public interface SQLExceptionListener {
    void onSqlException(SQLException exception);
}
