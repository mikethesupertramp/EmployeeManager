package me.mikethesupertramp.employeemanager.persistence;

import java.util.Objects;

public class Employee {
    private int id;
    private String firstName;
    private String lastName;
    private boolean present;
    private boolean late;
    private long cardID1;
    private long cardID2;

    public Employee() {

    }

    public Employee(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Employee(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Employee(int id, String firstName, String lastName, int cardID1, int cardID2) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cardID1 = cardID1;
        this.cardID2 = cardID2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getCardID1() {
        return cardID1;
    }

    public void setCardID1(long cardID1) {
        this.cardID1 = cardID1;
    }

    public long getCardID2() {
        return cardID2;
    }

    public void setCardID2(long cardID2) {
        this.cardID2 = cardID2;
    }

    public boolean isLate() {
        return late;
    }

    public void setLate(boolean late) {
        this.late = late;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id &&
                present == employee.present &&
                late == employee.late &&
                cardID1 == employee.cardID1 &&
                cardID2 == employee.cardID2 &&
                Objects.equals(firstName, employee.firstName) &&
                Objects.equals(lastName, employee.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, present, late, cardID1, cardID2);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", present=" + present +
                ", late=" + late +
                ", cardID1=" + cardID1 +
                ", cardID2=" + cardID2 +
                '}';
    }
}