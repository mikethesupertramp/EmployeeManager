package me.mikethesupertramp.employeemanager.domain;

public interface CardListener {
    void onCardEnter(long id);

    void onCardExit(long id);
}
