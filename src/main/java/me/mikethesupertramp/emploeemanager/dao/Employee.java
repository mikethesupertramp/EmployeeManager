package me.mikethesupertramp.emploeemanager.dao;

public class Employee {
    private int id;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private int cardID1;
    private int cardID2;

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

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public int getCardID1() {
        return cardID1;
    }

    public void setCardID1(int cardID1) {
        this.cardID1 = cardID1;
    }

    public int getCardID2() {
        return cardID2;
    }

    public void setCardID2(int cardID2) {
        this.cardID2 = cardID2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (id != employee.id) return false;
        if (cardID1 != employee.cardID1) return false;
        if (cardID2 != employee.cardID2) return false;
        if (firstName != null ? !firstName.equals(employee.firstName) : employee.firstName != null) return false;
        if (lastName != null ? !lastName.equals(employee.lastName) : employee.lastName != null) return false;
        return jobTitle != null ? jobTitle.equals(employee.jobTitle) : employee.jobTitle == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (jobTitle != null ? jobTitle.hashCode() : 0);
        result = 31 * result + cardID1;
        result = 31 * result + cardID2;
        return result;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", cardID1=" + cardID1 +
                ", cardID2=" + cardID2 +
                '}';
    }
}