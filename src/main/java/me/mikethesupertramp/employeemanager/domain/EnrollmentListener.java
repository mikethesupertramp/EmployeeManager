package me.mikethesupertramp.employeemanager.domain;

import me.mikethesupertramp.employeemanager.persistence.Employee;

public interface EnrollmentListener {
    void onEmployeeEnter(Employee employee);

    void onEmployeeExit(Employee employee);
}
