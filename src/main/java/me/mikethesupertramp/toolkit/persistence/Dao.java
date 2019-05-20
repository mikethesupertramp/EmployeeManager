package me.mikethesupertramp.toolkit.persistence;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> get(int id);

    List<T> getAll();

    boolean insert(T dto);

    boolean update(T dto);

    boolean delete(T dto);
}
