package me.mikethesupertramp.toolkit.persistence;

public interface DataChangeListener<T> {
    void onDataChanged(DataChangeEvent<T> e);
}
