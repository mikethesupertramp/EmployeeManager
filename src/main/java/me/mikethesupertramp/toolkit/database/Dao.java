package me.mikethesupertramp.toolkit.database;

import java.util.Optional;
import java.util.Set;

public interface Dao<T> {
    Optional<T> get(int id);

    Set<T> getAll();

    boolean insert(T t);

    boolean update(T t);

    boolean delete(T t);
}
