package me.mikethesupertramp.toolkit.persistence;


public class DataChangeEvent<T> {
    private TYPE type;
    private T dto;

    public DataChangeEvent(TYPE type, T dto) {
        this.type = type;
        this.dto = dto;
    }

    public TYPE getType() {
        return type;
    }

    public T getDto() {
        return dto;
    }

    public enum TYPE {INSERT, UPDATE, DELETE}
}
