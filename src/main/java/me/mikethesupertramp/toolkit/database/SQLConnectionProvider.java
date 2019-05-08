package me.mikethesupertramp.toolkit.database;

import java.sql.Connection;

public interface SQLConnectionProvider {
    Connection getConnection();
}
