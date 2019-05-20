package me.mikethesupertramp.toolkit.persistence;

import java.sql.Connection;

public interface SQLConnectionProvider {
    Connection getConnection();
}
