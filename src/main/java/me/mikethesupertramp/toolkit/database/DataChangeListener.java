package me.mikethesupertramp.toolkit.database;

public interface DataChangeListener<T> {
    void onDataChanged(DataChangeEvent e);
}
